package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import de.swtp13.creditportbackend.v1.requests.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<List<Procedure>> getAllProcedures() {
        System.out.println("Get all requests");
        return ResponseEntity.ok(procedureRepository.findAll());
    }

    /**
     * GET by ID
     * @return procedure with specific ID
     */
    @GetMapping("/{procedureId}")
    public ResponseEntity<Procedure> getProcedureById(@PathVariable int procedureId) {
        return procedureRepository.findById(procedureId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT by ID
     * updates a procedure with specific ID in the Database
     */
    /*@PutMapping("/{id}")
    public ResponseEntity<Procedure> updateProcedure(@PathVariable String procedureId, @RequestBody Procedure procedure) {
        if (!procedureService.existsById(procedureId)) {
            return ResponseEntity.notFound().build();
        }
        Procedure updatedProcedure = procedureService.updateProcedure(procedureId, procedure);
        return ResponseEntity.ok(updatedProcedure);
    }
     */

    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> createProcedure(@RequestBody ProcedureRequestDTO procedureRequestDTO) {
        ProcedureResponseDTO response = procedureService.createProcedureFromDTO(procedureRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}