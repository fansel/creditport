package de.swtp13.creditportbackend.v1.courses;

import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
List<Course> findAllByInternalModulesContains(InternalModule module);
}
