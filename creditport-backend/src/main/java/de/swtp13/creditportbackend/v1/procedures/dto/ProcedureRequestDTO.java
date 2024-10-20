package de.swtp13.creditportbackend.v1.procedures.dto;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.universities.University;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProcedureRequestDTO {
    private String annotation;
    private UUID universityId;
    private UUID courseId;
    private List<RequestDTO> requests;
}
