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


import java.util.List;

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
        //List<Procedure> proceduresWithRequests = procedureService.getProceduresWithRequests();
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
       // List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);
        return procedureRepository.findByProcedureId(procedureId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        /*try {
            procedure.setRequests(requests);
        } catch(NullPointerException e){
            return ResponseEntity.notFound().build();
        }*/
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

    @Operation(summary = "creates a procedure", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Procedure.class))),
            @ApiResponse(responseCode = "400", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Procedure> createProcedure(@RequestBody Procedure procedure) {
        Procedure newProcedure = new Procedure(
                procedure.getAnnotation(),
                procedure.getUniversity(),
                procedure.getCourseName(),
                procedure.getRequests());
        if (newProcedure == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(newProcedure);
    }
}