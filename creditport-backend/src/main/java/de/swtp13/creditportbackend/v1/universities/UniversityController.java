package de.swtp13.creditportbackend.v1.universities;

import de.swtp13.creditportbackend.v1.users.ManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    @Operation(summary = "returns a list of all universities with optional name filter", responses = {
            @ApiResponse(responseCode = "200", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = University.class))
            ))
    })
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
    @Operation(summary = "returns a single university", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = University.class))),
            @ApiResponse(responseCode = "404", description = "University id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<University> getUniversityById(@PathVariable UUID id) {
        return universityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Create a new university
    @Operation(summary = "creates a university", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = University.class)))
    })
    @PostMapping
    public ResponseEntity<?> createUniversity(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String token,
            @RequestBody University university) {
        System.out.println("Create University: " + university.getUniName());
        if (managementService.isAuthorized(token)) {
            university.setVerified(true);
        } else {
            university.setVerified(false);
        }
        return ResponseEntity.status(201).body(universityRepository.save(university));
    }



    // PUT: Update a university
    @Operation(summary = "updates the university with the given id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = University.class))),
            @ApiResponse(responseCode = "404", description = "University id not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<University> updateUniversity(@PathVariable UUID id,@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false, defaultValue = "") String token, @RequestBody University universityDetails) {
        return universityRepository.findById(id)
                .map(university -> {
                    university.setUniName(universityDetails.getUniName());
                    if(managementService.isAuthorized(token)){
                        university.setVerified(universityDetails.isVerified());
                    }

                    // Add other fields to update if needed
                    return ResponseEntity.ok(universityRepository.save(university));
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a university
    @Operation(summary = "deletes a request", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "University id not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable UUID id) {
        return universityRepository.findById(id)
                .map(university -> {
                    universityRepository.delete(university);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }



    // import university data from json file
    @Operation(summary = "creates all universities listed in the json of the body", responses = {
            @ApiResponse(responseCode = "200")
    })
    @PostMapping("/import")
    public ResponseEntity<List<University>> importUniversities(@RequestBody List<University> universities) {
        universityRepository.saveAll(universities);
        return ResponseEntity.ok(universities);
    }



}
