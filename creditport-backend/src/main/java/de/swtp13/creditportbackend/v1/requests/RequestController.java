package de.swtp13.creditportbackend.v1.requests;


        import de.swtp13.creditportbackend.v1.procedures.Procedure;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.io.ByteArrayResource;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.multipart.MultipartFile;


        import java.util.ArrayList;
        import java.util.List;
        import java.util.Objects;
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
    public ResponseEntity<RelatedRequestDTO> getRelatedRequestsById(@PathVariable int requestId){
        RelatedRequestDTO relatedRequest = new RelatedRequestDTO();

        Optional<Request> req = requestRepository.findByRequestId(requestId);
        Request request = req.orElse(null);
        try {
            relatedRequest.setRequestId(request.getRequestId());
            relatedRequest.setProcedureId(request.getProcedure().getProcedureId());
            relatedRequest.setExternalModule(request.getExternalModuleId());
            relatedRequest.setInternalModule(request.getInternalModuleId());
            relatedRequest.setAnnotation(request.getAnnotation());
            relatedRequest.setCreditPoints(request.getCreditPoints());
            relatedRequest.setStatus(request.getStatus());
            relatedRequest.setCreatedAt(request.getCreatedAt());
            relatedRequest.setPdfContent(request.getPdfContent());

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
    @GetMapping("/procedure/{procedureId}")
    public ResponseEntity<List<Request>> getRequestsByProcedure(@PathVariable int procedureId) {
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
    @PostMapping("/uploadPdf/{requestId}")
    public ResponseEntity<String> uploadRequestPdf(@PathVariable int requestId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !Objects.equals(file.getContentType(), "application/pdf")) {
            return ResponseEntity.badRequest().body("Invalid file. Please upload a PDF file.");
        }

        try {
            Request request = requestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));
            request.setPdfContent(file.getBytes());

            requestRepository.save(request);

            return ResponseEntity.ok("File uploaded successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }
    }
    @GetMapping("/downloadPdf/{requestId}")
    public ResponseEntity<ByteArrayResource> downloadRequestPdf(@PathVariable int requestId) {
        try {
            Request request = requestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

            byte[] pdfContent = request.getPdfContent();
            if (pdfContent == null || pdfContent.length == 0) {
                return ResponseEntity.notFound().build();
            }

            ByteArrayResource resource = new ByteArrayResource(pdfContent);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "attachment; filename=\"request_" + requestId + ".pdf\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ByteArrayResource(new byte[0]));
        }
    }
}
