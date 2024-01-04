package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.RequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.RequestResponseDTO;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.requests.Request;
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
        newProcedure.setUniversity(procedureRequestDTO.getUniversity());
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
            newRequest.setExternalModuleId(requestDTO.getExternalModule());
            newRequest.setInternalModuleId(requestDTO.getInternalModule());
            newRequest.setAnnotation(requestDTO.getAnnotation());
            newRequest.setCreditPoints(requestDTO.getCreditPoints());
            newRequest.setProcedure(newProcedure);
            newRequest.setStatus(de.swtp13.creditportbackend.v1.requests.Status.NICHT_BEARBEITET);
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
}
