package de.swtp13.creditportbackend;

import de.swtp13.creditportbackend.model.Person;
import de.swtp13.creditportbackend.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CreditPortApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditPortApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PersonRepository repository) {
		return (args) -> {
			// Daten speichern
			repository.save(new Person("Alice"));
			repository.save(new Person("Bob"));

			// Daten abrufen und ausgeben
			System.out.println("Personen gefunden mit findAll():");
			for (Person person : repository.findAll()) {
				System.out.println(person);
			}
		};
	}
}
