package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureRequestDTO;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;


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
        return ResponseEntity.ok(proceduresWithRequests);
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
        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}