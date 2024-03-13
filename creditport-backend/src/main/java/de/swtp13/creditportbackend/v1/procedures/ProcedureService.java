package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.courses.CourseDTO;
import de.swtp13.creditportbackend.v1.courses.CourseRepository;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModuleRepository;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
import de.swtp13.creditportbackend.v1.procedures.dto.*;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.requests.StatusRequest;
import de.swtp13.creditportbackend.v1.requests.dto.RequestDetailsDTO;
import de.swtp13.creditportbackend.v1.universities.University;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * Die Klasse enthält alle Methoden zu Vorgängen.
 */
@Service
public class ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UniversityRepository universityRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ExternalModuleRepository externalModuleRepository;
    @Autowired
    private InternalModuleRepository internalModuleRepository;


    private RequestDetailsDTO convertToRequestDetailsDTO(Request request) {
        RequestDetailsDTO dto = new RequestDetailsDTO();
        dto.setRequestId(request.getRequestId());
        dto.setExternalModules(request.getExternalModules());
        dto.setInternalModules(request.getInternalModules());
        dto.setAnnotationStudent(request.getAnnotationStudent());
        dto.setAnnotationCommittee(request.getAnnotationCommittee());
        dto.setStatusRequest(request.getStatusRequest());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setPdfExists(request.isPdfExists());
        dto.setModuleLink(request.getModuleLink());
        return dto;
    }

    // Angepasste Methode, um Procedures mit RequestDetailsDTOs abzurufen
    public List<ProcedureWithRequestsDTO> getProcedureDetailsWithRequests() {
        List<Request> requests = requestRepository.findAllWithProcedure();
        Map<Integer, ProcedureWithRequestsDTO> procedureMap = new HashMap<>();

        for (Request request : requests) {
            Procedure procedure = request.getProcedure();
            ProcedureWithRequestsDTO finalProcedureDTO = procedureMap.computeIfAbsent(procedure.getProcedureId(), k -> {
                ProcedureWithRequestsDTO newProcedureDTO = new ProcedureWithRequestsDTO();
                newProcedureDTO.setProcedureId(procedure.getProcedureId());
                newProcedureDTO.setStatus(procedure.getStatus());
                newProcedureDTO.setAnnotation(procedure.getAnnotation());
                newProcedureDTO.setUniversity(procedure.getUniversity());
                newProcedureDTO.setCourse(procedure.getCourse());
                newProcedureDTO.setCreatedAt(procedure.getCreatedAt());
                newProcedureDTO.setLastUpdated(procedure.getLastUpdated());
                newProcedureDTO.setRequestDetails(new ArrayList<>());
                return newProcedureDTO;
            });

            RequestDetailsDTO requestDetailsDTO = convertToRequestDetailsDTO(request);
            finalProcedureDTO.getRequestDetails().add(requestDetailsDTO);
        }

        return new ArrayList<>(procedureMap.values());
    }


    @Transactional
    public ProcedureResponseDTO createProcedureFromDTO(ProcedureRequestDTO procedureRequestDTO) {
        if (procedureRequestDTO.getRequests() == null || procedureRequestDTO.getRequests().isEmpty())
            return null;

        // Create a new Procedure entity
        Procedure newProcedure = new Procedure();

        newProcedure.setAnnotation(procedureRequestDTO.getAnnotation());
        Optional<Course> opCourse = courseRepository.findById(procedureRequestDTO.getCourseId());
        Optional<University> opUni = universityRepository.findById(procedureRequestDTO.getUniversityId());
        if (opUni.isEmpty() || opCourse.isEmpty()){
            return null;
        }
        newProcedure.setCourse(opCourse.get());
        newProcedure.setUniversity(opUni.get());
        newProcedure.setStatus(Status.NEU);
        newProcedure.setCreatedAt(Instant.now());
        newProcedure.setLastUpdated(newProcedure.getCreatedAt());
        newProcedure.setRequests(new ArrayList<>());

        // Save the procedure to get an ID
        newProcedure = procedureRepository.save(newProcedure);

        // Process and save each request
        List<RequestResponseDTO> requestResponseDTOs = new ArrayList<>();
        for (RequestDTO requestDTO : procedureRequestDTO.getRequests()) {
            Request newRequest = new Request();
            List<ExternalModule> externalModules = new ArrayList<>();
            for(UUID externalModuleId: requestDTO.getExternalModuleId()){
                Optional<ExternalModule> externalModule = externalModuleRepository.findById(externalModuleId);
                if(externalModule.isEmpty()){
                    return null;
                }
                externalModules.add(externalModule.get());

            }
            List<InternalModule> internalModules = new ArrayList<>();
            for(UUID internalModuleId: requestDTO.getInternalModuleId()){
                Optional<InternalModule> internalModule = internalModuleRepository.findById(internalModuleId);
                if(internalModule.isEmpty()){
                    return null;
                }
                internalModules.add(internalModule.get());
            }
            newRequest.setAnnotationStudent(requestDTO.getAnnotationStudent());
            newRequest.setAnnotationCommittee(requestDTO.getAnnotationCommittee());
            newRequest.setModuleLink(requestDTO.getModuleLink());
            newRequest.setProcedure(newProcedure);
            newRequest.setStatusRequest(StatusRequest.NICHT_BEARBEITET);
            newRequest.setCreatedAt(Instant.now());
            newRequest = requestRepository.save(newRequest);
            RequestResponseDTO requestResponseDTO = new RequestResponseDTO();
            requestResponseDTO.setRequestId(newRequest.getRequestId());
            requestResponseDTOs.add(requestResponseDTO);
        }

        ProcedureResponseDTO responseDTO = new ProcedureResponseDTO();
        responseDTO.setProcedureId(newProcedure.getProcedureId());
        responseDTO.setRequests(requestResponseDTOs);

        return responseDTO;
    }

    public void setStatusOffen(Procedure procedure){
        if (procedure.getStatus().equals(Status.NEU)){
            procedure.setStatus(Status.OFFEN);
            procedureRepository.save(procedure);
        }
    }

    public void setStatusArchiviert(Procedure procedure){
        if (procedure.getStatus().equals(Status.VOLLSTÄNDIG)){
            procedure.setStatus(Status.ARCHIVIERT);
            procedureRepository.save(procedure);
        }
    }

}
