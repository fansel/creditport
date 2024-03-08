package de.swtp13.creditportbackend.v1.requests;


import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


/**
 * @author Frida
 * Die Klasse ist ein Controller für die Anträge.
 * REST-Schnittstelle: /api/requests
 */
@RestController
@RequestMapping("/requests")
public class RequestController {


    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ProcedureRepository procedureRepository;


    @Operation(summary = "returns a list of all requests", responses = {
            @ApiResponse(responseCode = "200" //content = @Content(
                    //mediaType = "application/json",
                    //array = @ArraySchema(schema = @Schema(implementation = Request.class))
            )//)
    })
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        System.out.println("Get all requests");
        return ResponseEntity.ok(requestRepository.findAll());
    }

    // GET Request by RequestID
    @Operation(summary = "returns a single request", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Request.class))),
            @ApiResponse(responseCode = "404", description = "Request id not found",
                    content = @Content)
    })
    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable int requestId) {
        return requestRepository.findByRequestId(requestId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: related Requests by RequestID
    @GetMapping("/related/{requestId}")
    public ResponseEntity<RelatedRequestDTO> getRelatedRequestsById(@PathVariable int requestId){
        RelatedRequestDTO relatedRequest = new RelatedRequestDTO();

        Optional<Request> req = requestRepository.findByRequestId(requestId);
        Request request = req.orElse(null);
        try {
            relatedRequest.setRequestId(request.getRequestId());
            relatedRequest.setProcedureId(request.getProcedure().getProcedureId());
            relatedRequest.setExternalModules(request.getExternalModules());
            relatedRequest.setInternalModules(request.getInternalModules());
            relatedRequest.setAnnotationStudent(request.getAnnotationStudent());
            relatedRequest.setAnnotationCommittee(request.getAnnotationCommittee());
            relatedRequest.setStatusRequest(request.getStatusRequest());
            relatedRequest.setCreatedAt(request.getCreatedAt());
            relatedRequest.setPdfExists(request.isPdfExists());

            Procedure proc = requestRepository.findProcedureByRequestId(requestId);
            List<Integer> requestIds = requestRepository.findAllRelatedRequests(proc.getProcedureId());

            if (requestIds.contains(requestId)){
                requestIds.remove((Integer) requestId);
            }
            relatedRequest.setRelatedRequests(requestIds);
            return ResponseEntity.ok(relatedRequest);
        } catch (NullPointerException e){
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Request by ProcedureId
    @Operation(summary = "returns a list of requests for the given procedure", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Request.class)))),
            @ApiResponse(responseCode = "404", description = "Procedure id not found",
                    content = @Content)
    })
    @GetMapping("/procedure/{procedureId}")
    public ResponseEntity<List<Request>> getRequestsByProcedure(@PathVariable int procedureId) {
        Optional<Procedure> procedure = procedureRepository.findByProcedureId(procedureId);
        return procedure.map(value -> ResponseEntity.ok(value.getRequests())).orElseGet(() -> ResponseEntity.notFound().build());
        //List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);

    }


    // POST: Create a new Request
    @Operation(summary = "creates a Request", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)))
    })
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        //System.out.println("Create Request: " + request.getRequestId());
        return ResponseEntity.ok(requestRepository.save(request));
    }

    // PUT: Update a Request
    @Operation(summary = "updates the request with the given id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Request.class))),
            @ApiResponse(responseCode = "404", description = "Request id not found", content = @Content)
    })
    @PutMapping("/{requestId}")
    public ResponseEntity<Request> updateRequestStatus(@PathVariable int requestId, @RequestBody Request RequestDetails) {
        return requestRepository.findByRequestId(requestId)
                .map(Request -> {
                    Request.setStatusRequest(RequestDetails.getStatusRequest());
                    Request.setExternalModules(RequestDetails.getExternalModules());
                    Request.setInternalModules(RequestDetails.getInternalModules());
                    Request.setAnnotationStudent(RequestDetails.getAnnotationStudent());
                    Request.setAnnotationCommittee(RequestDetails.getAnnotationCommittee());
                    Request.setPdfExists(RequestDetails.isPdfExists());
                    Request.setModuleLink(RequestDetails.getModuleLink());
                    // Add other fields to update if needed
                    Request updatedRequest = requestRepository.save(Request);
                    return ResponseEntity.ok(updatedRequest);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Request
    @Operation(summary = "deletes a request", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "404", description = "Request id not found", content = @Content)
    })
    @DeleteMapping("/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable int requestId) {
        return requestRepository.findByRequestId(requestId)
                .map(Request -> {
                    requestRepository.delete(Request);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
