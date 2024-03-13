package de.swtp13.creditportbackend.v1.procedures.dto;

import de.swtp13.creditportbackend.v1.courses.Course;
import de.swtp13.creditportbackend.v1.courses.CourseDTO;
import de.swtp13.creditportbackend.v1.procedures.Status;
import de.swtp13.creditportbackend.v1.requests.dto.RequestDetailsDTO;
import de.swtp13.creditportbackend.v1.universities.University;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ProcedureWithRequestsDTO {
    private int procedureId;
    private String annotation;
    private University university;
    private Course course;
    private Instant createdAt;
    private Status status;
    private Instant lastUpdated;
    private List<RequestDetailsDTO> requestDetails;

    // Konstruktoren, Getter und Setter
}
