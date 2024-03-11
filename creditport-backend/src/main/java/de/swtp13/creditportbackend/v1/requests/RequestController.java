package de.swtp13.creditportbackend.v1.requests;


import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModuleRepository;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModuleRepository;
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


import java.util.*;


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
    @Autowired
    private RequestService requestService;
    @Autowired
    private InternalModuleRepository internalModuleRepository;
    @Autowired
    private ExternalModuleRepository externalModuleRepository;

    @Operation(summary = "returns a list of all requests", responses = {
            @ApiResponse(responseCode = "200" //content = @Content(
                    //mediaType = "application/json",
                    //array = @ArraySchema(schema = @Schema(implementation = Request.class))
            )//)
    })
    @GetMapping
    public ResponseEntity<List<UpdateRequestDTO>> getAllRequests() {
        System.out.println("Get all requests");
        List<UpdateRequestDTO> requestDTOS = new ArrayList<>();
        for(Request request: requestRepository.findAll()){
            requestDTOS.add(requestService.toUpdateRequestDTO(request));
        }
        return ResponseEntity.ok(requestDTOS);
    }

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
    @GetMapping("/similar/{requestId}")
    public List<Request> getSimilarRequests(@PathVariable int requestId){
        List<Request> similarRequests = new ArrayList<>();
        if (!requestRepository.findByRequestId(requestId).isPresent()){
        }else{
        Request request = requestRepository.findByRequestId(requestId).get();
        List<Request> approvedRequests = requestRepository.getModulesFromApprovedRequests();
        for(Request approvedRequest: approvedRequests){
            if(
                   approvedRequest.getExternalModuleIds().equals(request.getExternalModuleIds())
                           &&
                   approvedRequest.getInternalModuleIds().equals(request.getInternalModuleIds())
            ){
                similarRequests.add(requestRepository.findByRequestId(approvedRequest.getRequestId()).get());
            }
        }
        }
        return similarRequests;
    }


    // POST: Create a new Request
    @Operation(summary = "creates a Request", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Request.class)))
    })
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody UpdateRequestDTO RequestDetails) {
        if(!procedureRepository.findByProcedureId(RequestDetails.getProcedureId()).isPresent()){
            return ResponseEntity.notFound().build();
        }
        Procedure procedure = procedureRepository.findByProcedureId(RequestDetails.getProcedureId()).get();
        List<InternalModule> internalModules = new ArrayList<>();
        for (UUID internalModuleId: RequestDetails.getInternalModuleIds()){
            if (internalModuleRepository.existsById(internalModuleId)){
                internalModules.add(internalModuleRepository.findById(internalModuleId).get());
            } else{
                return ResponseEntity.notFound().build();
            }
        }
        List<ExternalModule> externalModules = new ArrayList<>();
        for(UUID externalModuleId:RequestDetails.getExternalModuleIds()){
            if(externalModuleRepository.existsById(externalModuleId)){
                externalModules.add(externalModuleRepository.findById(externalModuleId).get());
            } else{
                ResponseEntity.notFound().build();
            }
        }
        Request newRequest = new Request(
            procedure, externalModules, internalModules, RequestDetails.getAnnotationStudent(), RequestDetails.getAnnotationCommittee()
        );
        return ResponseEntity.ok(requestRepository.save(newRequest));
    }

    // PUT: Update a Request
    @Operation(summary = "updates the request with the given id", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Request.class))),
            @ApiResponse(responseCode = "404", description = "Request id not found", content = @Content)
    })
    @PutMapping("/{requestId}")
    public ResponseEntity<Request> updateRequest(@PathVariable int requestId, @RequestBody UpdateRequestDTO RequestDetails) {
            return requestService.updateRequest(RequestDetails,requestId);
    }

    @PutMapping("/approval/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable int requestId, @RequestBody Request RequestDetails) {
        if (RequestDetails.getAnnotationCommittee().strip() == ""){
            return ResponseEntity.badRequest().body("Committee Annotation is not allowed to be null!");
        }
        return requestRepository.findByRequestId(requestId)
                .map(Request -> {
                    Request.setStatusRequest(RequestDetails.getStatusRequest());
                    Request.setAnnotationCommittee(RequestDetails.getAnnotationCommittee());
                    Request updatedRequest = requestRepository.save(Request);
                    return ResponseEntity.ok(updatedRequest);
                }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/favored/{id}")
    public ResponseEntity<Request> favoredProcedure(@PathVariable Integer id) {
        Optional<Request> optionalRequest = requestRepository.findByRequestId(id);
        Request request = optionalRequest.orElse(null);
        if (request != null) requestService.setFavored(request);
        return ResponseEntity.ok(request);
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
