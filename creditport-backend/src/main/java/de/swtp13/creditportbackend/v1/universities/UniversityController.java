package de.swtp13.creditportbackend.v1.universities;

import de.swtp13.creditportbackend.v1.config.JwtService;
import de.swtp13.creditportbackend.v1.users.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ManagementService managementService;


    // GET all universities with optional name filter
    @GetMapping
    public ResponseEntity<List<University>> getAllUniversities(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            System.out.println("Get Universities with uniName like: " + name);
            return ResponseEntity.ok(universityRepository.findByUniNameContainingIgnoreCase(name));
        } else {
            System.out.println("Get all Universities");
            return ResponseEntity.ok(universityRepository.findAll());
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
    public ResponseEntity<?> createUniversity(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String token,
            @RequestBody University university) {
        System.out.println("Create University: " + university.getUniName());
        if (managementService.isAuthorized(token)) {
            return ResponseEntity.ok(universityRepository.save(university));
        } else {
            university.setVerified(false);
           return ResponseEntity.ok(universityRepository.save(university));
        }
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



    // import university data from json file
    @PostMapping("/import")
    public ResponseEntity<String> importUniversities(@RequestBody List<University> universities) {
        universityRepository.saveAll(universities);
        return ResponseEntity.ok("Universities imported");
    }



}
