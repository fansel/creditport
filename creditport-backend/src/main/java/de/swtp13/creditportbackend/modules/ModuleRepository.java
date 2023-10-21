package de.swtp13.creditportbackend.modules.repository;
import de.swtp13.creditportbackend.modules.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Felix
 * Dieses Interface ist ein Repository für Module.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
}
