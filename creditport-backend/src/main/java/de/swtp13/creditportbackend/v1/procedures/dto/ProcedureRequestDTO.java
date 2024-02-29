package de.swtp13.creditportbackend.v1.procedures.dto;

import de.swtp13.creditportbackend.v1.universities.University;
import lombok.Data;

import java.util.List;

@Data
public class ProcedureRequestDTO {
    private String annotation;
    private University university;
    private String courseName;
    private List<RequestDTO> requests;
}
