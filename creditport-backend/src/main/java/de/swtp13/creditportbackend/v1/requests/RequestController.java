package de.swtp13.creditportbackend.v1.requests;


import de.swtp13.creditportbackend.v1.procedures.Procedure;
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



    // GET all requests
    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        System.out.println("Get all requests");
        return ResponseEntity.ok(requestRepository.findAll());
    }

    // GET Request by RequestID
    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable int requestId) {
        return requestRepository.findByRequestId(requestId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: related Requests by RequestID
    @GetMapping("/relatedRequests/{requestId}")
    public ResponseEntity<List<Integer>> getRelatedRequestsById(@PathVariable int requestId){
        Procedure proc = requestRepository.findProcedureByRequestId(requestId);
        List<Integer> requestIds = requestRepository.findAllRelatedRequests(proc.getProcedureId());
        return ResponseEntity.ok(requestIds);
    }

    // GET: Request by ProcedureId
    @GetMapping("/procedure/{procedureId}")
    public ResponseEntity<List<Request>> getRequestsByProcedure(@PathVariable String procedureId) {
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);
        return ResponseEntity.ok(requests);
    }

    // POST: Create a new Request
    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        //System.out.println("Create Request: " + request.getRequestId());
        return ResponseEntity.ok(requestRepository.save(request));
    }

    // PUT: Update a Request
    @PutMapping("/{requestId}")
    public ResponseEntity<Request> updateRequestStatus(@PathVariable int requestId, @RequestBody Request RequestDetails) {
        return requestRepository.findByRequestId(requestId)
                .map(Request -> {
                    Request.setStatus(RequestDetails.getStatus());
                    // Add other fields to update if needed
                    Request updatedRequest = requestRepository.save(Request);
                    return ResponseEntity.ok(updatedRequest);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete a Request
    @DeleteMapping("/{requestId}")
    public ResponseEntity<?> deleteRequest(@PathVariable int requestId) {
        return requestRepository.findByRequestId(requestId)
                .map(Request -> {
                    requestRepository.delete(Request);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
