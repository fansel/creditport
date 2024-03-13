package de.swtp13.creditportbackend.v1.courses;

import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.objenesis.SpringObjenesis;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO{
    private UUID courseId;
    private String courseName;
    private List<InternalModule> internalModules;
}
