package de.swtp13.creditportbackend.procedures.repository;


import de.swtp13.creditportbackend.procedures.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, String> {

}
