package de.swtp13.creditportbackend.v1.internalmodules;
import de.swtp13.creditportbackend.v1.courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Felix
 * Dieses Interface ist ein Repository für Module.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 */
@Repository
public interface InternalModuleRepository extends JpaRepository<InternalModule, UUID> {
    List<InternalModule> findInternalModulesByCoursesContains(Course course);
}
