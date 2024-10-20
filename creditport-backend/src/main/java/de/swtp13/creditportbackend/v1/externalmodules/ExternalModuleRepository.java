package de.swtp13.creditportbackend.v1.externalmodules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Felix
 * Dieses Interface ist ein Repository für Module.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 */
@Repository
public interface ExternalModuleRepository extends JpaRepository<ExternalModule, UUID> {
}
