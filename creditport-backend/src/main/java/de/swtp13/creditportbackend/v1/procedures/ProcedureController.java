package de.swtp13.creditportbackend.v1.procedures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/procedure
 */
@RestController
@RequestMapping("/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureRepository procedureRepository;
    private final ProcedureService procedureService;

    /**
     * Constructs a {@code ProcedureController} with the necessary service.
     *
     * @param procedureService the service that will handle the business logic for {@code Procedure} entities.
     */
    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    /**
     * POST
     * Creates a new {@code Procedure} entity based on the provided data.
     *
     * @param procedure the {@code Procedure} object containing the data for the new entity.
     * @return a {@code ResponseEntity} containing the created {@code Procedure} and the HTTP status.
     */
    @PostMapping
    public ResponseEntity<Procedure> createProcedure(@RequestBody Procedure procedure) {
        Procedure newProcedure = procedureService.createProcedure(procedure);
        return new ResponseEntity<>(newProcedure, HttpStatus.CREATED);
    }

    /**
     * GET
     * @author Maike
     * @return List of procedures in JSON Format
     */
    @GetMapping
    public ResponseEntity<List<Procedure>> getAllProceduresWithDetails() {
        List<Procedure> procedures = procedureRepository.findAllWithDetails();
        return ResponseEntity.ok(procedures);
    }

    /**
     * GET by ID
     * @author Maike
     * @return List of procedures in JSON Format
     */
    @GetMapping("/{id}")
    public ResponseEntity<Procedure> getProcedureById(@PathVariable String procedureId) {
        return procedureRepository.findById(procedureId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<Procedure> updateProcedure(@PathVariable String procedureId, @RequestBody Procedure procedure) {
        if (!procedureService.existsById(procedureId)) {
            return ResponseEntity.notFound().build();
        }
        Item updatedProcedure = procedureService.updateProcedure(procedureId, procedure);
        return ResponseEntity.ok(updatedProcedure);
    }
}