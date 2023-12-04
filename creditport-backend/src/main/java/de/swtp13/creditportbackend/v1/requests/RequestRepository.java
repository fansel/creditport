package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Dieses Interface ist ein Repository für Anträge.
 * Es stellt Methoden zum Zugriff auf die Datenbank zur Verfügung.
 * Es erbt von JpaRepository, welches bereits Methoden zum Zugriff auf die Datenbank bereitstellt.
 * Die Methoden sind in der Dokumentation von JpaRepository zu finden.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request,String> {

    Optional<Request> findByRequestId(int requestID);
    @Query(value = "SELECT Requests FROM Requests r where r.procedure.procedureId = ?1")
    List<Request> findRequestsByProcedureId(String procedureId);
}
