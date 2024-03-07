package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.RequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.RequestResponseDTO;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.StatusRequest;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public List<Procedure> getProceduresWithRequests() {
        List<Request> requests = requestRepository.findAllWithProcedure();
        Map<Integer, Procedure> procedureMap = new HashMap<>();
        for (Request request : requests) {
            Procedure procedure = request.getProcedure();
            Procedure finalProcedure = procedureMap.computeIfAbsent(procedure.getProcedureId(), k -> {
                Procedure newProcedure = new Procedure();
                newProcedure.setProcedureId(procedure.getProcedureId());
                newProcedure.setStatus(procedure.getStatus());
                newProcedure.setAnnotation(procedure.getAnnotation());
                newProcedure.setCourseName(procedure.getCourseName());
                newProcedure.setUniversity(procedure.getUniversity());
                newProcedure.setLastUpdated(procedure.getLastUpdated());
                newProcedure.setCreatedAt(procedure.getCreatedAt());
                newProcedure.setRequests(new ArrayList<>());
                return newProcedure;
                });
            finalProcedure.getRequests().add(request);
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
        newProcedure.setCourseName(procedureRequestDTO.getCourseName());
        ResponseEntity.ok(universityRepository.findById(procedureRequestDTO.getUniversityId()));
        //newProcedure.setStatus(Status.NEU);
        //newProcedure.setCreatedAt(Instant.now());
        //newProcedure.setLastUpdated(newProcedure.getCreatedAt());
        newProcedure.setRequests(new ArrayList<>());

        // Save the procedure to get an ID
        newProcedure = procedureRepository.save(newProcedure);

        // Process and save each request
        List<RequestResponseDTO> requestResponseDTOs = new ArrayList<>();
        for (RequestDTO requestDTO : procedureRequestDTO.getRequests()) {
            Request newRequest = new Request();
            newRequest.setExternalModules(requestDTO.getExternalModules());
            newRequest.setInternalModules(requestDTO.getInternalModules());
            newRequest.setAnnotationStudent(requestDTO.getAnnotationStudent());
            newRequest.setAnnotationCommittee(requestDTO.getAnnotationCommittee());
            newRequest.setCreditPoints(requestDTO.getCreditPoints());
            newRequest.setProcedure(newProcedure);
            newRequest.setStatusRequest(StatusRequest.NICHT_BEARBEITET);
            newRequest.setCreatedAt(Instant.now());
            newRequest.setModuleLink(requestDTO.getModuleLink());
            newRequest = requestRepository.save(newRequest);
            RequestResponseDTO requestResponseDTO = new RequestResponseDTO();
            requestResponseDTO.setRequestId(String.valueOf(newRequest.getRequestId()));
            requestResponseDTOs.add(requestResponseDTO);
        }

        ProcedureResponseDTO responseDTO = new ProcedureResponseDTO();
        responseDTO.setProcedureId(String.valueOf(newProcedure.getProcedureId()));
        responseDTO.setRequests(requestResponseDTOs);

        return responseDTO;
    }
/* Vorbereitung für Statusänderungen
    public void setStatusInBearbeitung(Procedure procedure){
        boolean bearbeitet = false;
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedure.getProcedureId());
        for (Request request : requests){
            if (request.getStatusRequest().equals(StatusRequest.BEARBEITET)) {
                bearbeitet = true;
                break;
            }
        }
        if (procedure.getStatus().equals(Status.OFFEN)){
            procedure.setStatus(Status.IN_BEARBEITUNG);
        }
    }

    public void setStatusVollstaendig(Procedure procedure){
        boolean bearbeitet = true;
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedure.getProcedureId());
        for (Request request : requests){
            if (!(request.getStatusRequest().equals(StatusRequest.ABGELEHNT)) && !(request.getStatusRequest().equals(StatusRequest.ANGENOMMEN))) {
                bearbeitet = false;
                break;
            }
        }
        if (procedure.getStatus().equals(Status.WEITERGELEITET)){
            procedure.setStatus(Status.VOLLSTÄNDIG);
        }
    }

 */
}
