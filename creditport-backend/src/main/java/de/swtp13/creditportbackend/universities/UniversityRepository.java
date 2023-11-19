package de.swtp13.creditportbackend.universities;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Dieses Interface ist ein Repository für Module.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 */
@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {
    List<University> findByUniNameContainingIgnoreCase(String uniName);
}
