package de.swtp13.creditportbackend.v1.procedures;


import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.procedures.dto.ProcedureResponseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Maike
 * Die Klasse ist ein Controller für die Vorgänge.
 * REST-Schnittstelle: /api/v1/procedures
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
    @Operation(summary = "returns a list of all procedures", responses = {
            @ApiResponse(responseCode = "200", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Procedure.class))
            ))
    })
    @GetMapping
    public ResponseEntity<List<Procedure>> getProceduresWithRequests() {
        List<Procedure> proceduresWithRequests = procedureService.getProceduresWithRequests();
        return ResponseEntity.ok(procedureRepository.findAll());
    }

    /**
     * GET by ID
     * @return procedure with specific ID
     */
    @Operation(summary = "returns a single procedure", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Procedure.class))),
            @ApiResponse(responseCode = "404", description = "Procedure id not found",
                    content = @Content)
    })
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

    @Operation(summary = "returns a list of all procedure ids", responses = {
            @ApiResponse(responseCode = "200")
    })
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
    @Operation(summary = "updates the procedure with the given id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Procedure.class))),
            @ApiResponse(responseCode = "404", description = "Procedure id not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Procedure> updateProcedure(@PathVariable("id") int procedureId, @RequestBody Procedure ProcedureDetails) {
        return procedureRepository.findByProcedureId(procedureId)
                .map(procedure -> {
                    procedure.setStatus(ProcedureDetails.getStatus());
                    procedure.setAnnotation(ProcedureDetails.getAnnotation());
                    procedure.setUniversity(ProcedureDetails.getUniversity());
                    procedure.setCourseName(ProcedureDetails.getCourseName());
                    procedure.setRequests(ProcedureDetails.getRequests());
                    // Add other fields to update if needed
                    Procedure updatedProcedure = procedureRepository.save(procedure);
                    return ResponseEntity.ok(updatedProcedure);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "creates a procedure", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Procedure.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> createProcedure(@RequestBody ProcedureRequestDTO procedureDetails) {
        ProcedureResponseDTO response = procedureService.createProcedureFromDTO(procedureDetails);
        if (response == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "sets status of given procedure to 'WEITERGELEITET'", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content)
    })
    @PostMapping("/forward/{id}")
    public ResponseEntity<?> forwardProcedure(@PathVariable("id") int id) {
        return procedureRepository.findByProcedureId(id)
                .map(procedure -> {
                    procedure.setStatus(Status.WEITERGELEITET);
                    Procedure updatedProcedure = procedureRepository.save(procedure);
                    return ResponseEntity.ok(updatedProcedure);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "deletes a procedure", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Procedure id not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProcedure(@PathVariable int id) {
        return procedureRepository.findById(id)
                .map(procedure -> {
                    procedureRepository.delete(procedure);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}