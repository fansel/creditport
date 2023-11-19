package de.swtp13.creditportbackend.procedures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Dieses Interface ist ein Repository für Vorgänge.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 * Zusätzlich sind auch selbst definierte Datenbankabfragen verfügbar.
 */
public interface ProcedureRepository extends JpaRepository<Procedure, String> {
    /**
     * Liefert alle Vorgänge mit den entsprechenden Anträgen, Modulen und Universität zurück.
     * @return
     */
    @Query("SELECT p FROM Procedures p") //TODO: Query vervollständigen
    List<Procedure> findAllWithDetails();
}
