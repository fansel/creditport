package de.swtp13.creditportbackend.v1.procedures.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcedureResponseDTO {
    private int procedureId;
    private List<RequestResponseDTO> requests;
}
