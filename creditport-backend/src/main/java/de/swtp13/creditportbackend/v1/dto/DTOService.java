package de.swtp13.creditportbackend.v1.dto;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class DTOService {

    private final DTORepository procedureRequestRepository;

    /**
     * Mapped die Abfrage Ergebnisse auf ein DTO.
     * @return eine Liste mit allen DTOs aus der Anfrage.
     */
    public List<ProcedureRequestDTO> getProcedureRequests() {
        List<Object[]> results = procedureRequestRepository.findAllProcedureRequestDTOs();
        List<ProcedureRequestDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            dtos.add(new ProcedureRequestDTO(
                    (String) result[0], // id
                    (String) result[1], // status
                    (String) result[2], // proc_annotation
                    ((Timestamp) result[3]).toLocalDateTime(), // proc_created_on
                    (Integer) result[4], // request_id
                    (String) result[5], // external_module_id
                    (String) result[6], // internal_module_id
                    (String) result[7], // req_annotation
                    (Integer) result[8], // credit_points
                    ((Timestamp) result[9]).toLocalDateTime() // req_created_on
            ));
        }
        return dtos;
    }
}