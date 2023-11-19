package de.swtp13.creditportbackend.procedures;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, String> {
    @Query("SELECT p FROM Procedures p")
    List<Procedure> findAllWithDetails();
}
