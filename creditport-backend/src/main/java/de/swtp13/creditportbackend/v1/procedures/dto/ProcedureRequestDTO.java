package de.swtp13.creditportbackend.v1.procedures.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProcedureRequestDTO {
    private String annotation;
    private String university;
    private String courseName;
    private List<RequestDTO> requests;
}
