package de.swtp13.creditportbackend.v1.courses;

import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private InternalModuleRepository internalModuleRepository;

    public void addInternalModules(Course course){
        if (course.getInternalModules().isEmpty()) course.setInternalModules(internalModuleRepository.findInternalModulesByCoursesContains(course));
    }
}
