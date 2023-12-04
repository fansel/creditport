package de.swtp13.creditportbackend.v1.dto;

import de.swtp13.creditportbackend.v1.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.Procedure; // Assuming you have an entity called Procedure
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DTORepository extends JpaRepository<Procedure, Long> {

    @Query(value = "SELECT " +
            "p.id, p.status, p.annotation AS proc_annotation, p.created_on AS proc_created_on, " +
            "r.request_id, r.external_module_id, r.internal_module_id, r.annotation AS req_annotation, " +
            "r.credit_points, r.created_on AS req_created_on " +
            "FROM Procedures p " +
            "JOIN Requests r " +
            "ON p.id = r.procedure_id", nativeQuery = true)
    List<Object[]> findAllProcedureRequestDTOs();
}