package de.swtp13.creditportbackend.universities;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, UUID> {
    List<University> findByUniNameContainingIgnoreCase(String uniName);
}
