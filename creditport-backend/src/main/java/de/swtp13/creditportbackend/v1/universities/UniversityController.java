package de.swtp13.creditportbackend.v1.universities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Universitäten.
 * REST-Schnittstelle: /api/universities
 */
@RestController
@RequestMapping("/universities")
public class UniversityController {


    @Autowired
    private UniversityRepository universityRepository;


    // GET all universities with optional name filter
    @GetMapping

    public List<University> getAllUniversities(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            System.out.println("Get Universities with uniName like: " + name);
            return universityRepository.findByUniNameContainingIgnoreCase(name);
        } else {
            System.out.println("Get all Universities");
            return universityRepository.findAll();
        }
    }

    // GET university by ID
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable UUID id) {
        return universityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create a new university
    @PostMapping
    public University createUniversity(@RequestBody University university) {
        System.out.println("Create University: " + university.getUniName());
        return universityRepository.save(university);
    }

    // PUT: Update a university
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable UUID id, @RequestBody University universityDetails) {
        return universityRepository.findById(id)
                .map(university -> {
                    university.setUniName(universityDetails.getUniName());
                    // Add other fields to update if needed
                    University updatedUniversity = universityRepository.save(university);
                    return ResponseEntity.ok(updatedUniversity);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a university
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable UUID id) {
        return universityRepository.findById(id)
                .map(university -> {
                    universityRepository.delete(university);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
