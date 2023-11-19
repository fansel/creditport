package de.swtp13.creditportbackend.procedures;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedureRepository extends JpaRepository<Procedure, String> {
    @Query("SELECT p.procedureId, p.status, p.courseName FROM Procedure p" +
            "JOIN FETCH p.university u" +
            "JOIN FETCH p.request r " +
            "JOIN FETCH r.module m ")
    List<Procedure> findAllWithDetails();
}
