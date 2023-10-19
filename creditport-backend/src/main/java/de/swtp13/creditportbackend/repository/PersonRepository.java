package de.swtp13.creditportbackend.repository;

import de.swtp13.creditportbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
