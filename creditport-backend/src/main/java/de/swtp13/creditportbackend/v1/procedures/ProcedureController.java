package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import de.swtp13.creditportbackend.v1.universities.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/procedure
 */
@RestController
@RequestMapping("/procedures")
public class ProcedureController {

    @Autowired
    private ProcedureRepository procedureRepository;
    private final ProcedureService procedureService;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UniversityRepository universityRepository;

    /**
     * Constructs a {@code ProcedureController} with the necessary service.
     * @param procedureService the service that will handle the business logic for {@code Procedure} entities.
     */
    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    /**
     * GET
     * @return List of all procedures
     */
    @GetMapping
    public ResponseEntity<List<Procedure>> getProceduresWithRequests() {
        List<Procedure> proceduresWithRequests = procedureService.getProceduresWithRequests();
        return ResponseEntity.ok(procedureRepository.findAll());
    }

    /**
     * GET by ID
     * @return procedure with specific ID
     */

    @GetMapping("/{procedureId}")
    public ResponseEntity<Procedure> getProcedureById(@PathVariable int procedureId) {
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);
        Optional<Procedure> optionalProcedure = procedureRepository.findByProcedureId(procedureId);
        Procedure procedure = optionalProcedure.orElse(null);
        try {
            procedure.setRequests(requests);
        } catch(NullPointerException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(procedure);
    }
    @GetMapping("/ids")
    public ResponseEntity<List<Integer>> getProcedureIds(){
        List<Integer> ids = procedureRepository.findAllIds();
        return ResponseEntity.ok(ids);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Procedure> updateProcedure(@PathVariable String procedureId, @RequestBody Procedure procedure) {
        if (!procedureService.existsById(procedureId)) {
            return ResponseEntity.notFound().build();
        }
        Procedure updatedProcedure = procedureService.updateProcedure(procedureId, procedure);
        return ResponseEntity.ok(updatedProcedure);
    }
     */
    /**
     * PUT by ID
     * updates a procedure with specific ID in the Database
     */
    @PutMapping("/{id}")
    public ResponseEntity<Procedure> updateProcedure(@PathVariable("id") int procedureId, @RequestBody Procedure ProcedureDetails) {
        return procedureRepository.findByProcedureId(procedureId)
                .map(Procedure -> {
                    Procedure.setStatus(ProcedureDetails.getStatus());
                    Procedure.setAnnotation(ProcedureDetails.getAnnotation());
                    Procedure.setUniversity(ProcedureDetails.getUniversity());
                    Procedure.setCourseName(ProcedureDetails.getCourseName());
                    Procedure.setRequests(ProcedureDetails.getRequests());
                    // Add other fields to update if needed
                    Procedure updatedProcedure = procedureRepository.save(Procedure);
                    return ResponseEntity.ok(updatedProcedure);
                }).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> createProcedure(@RequestBody ProcedureRequestDTO procedureDetails) {
        ProcedureResponseDTO response = procedureService.createProcedureFromDTO(procedureDetails);
        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}