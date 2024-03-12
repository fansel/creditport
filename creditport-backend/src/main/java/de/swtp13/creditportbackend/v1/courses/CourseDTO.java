package de.swtp13.creditportbackend.v1.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO extends Course{
    private UUID courseId;
    private String courseName;
}
