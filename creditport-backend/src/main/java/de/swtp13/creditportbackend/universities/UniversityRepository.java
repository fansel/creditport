package de.swtp13.creditportbackend.universities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    List<University> findByUniNameContainingIgnoreCase(String uniName);
}
