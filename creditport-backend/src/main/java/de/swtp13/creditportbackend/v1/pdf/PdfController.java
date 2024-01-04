package de.swtp13.creditportbackend.v1.pdf;

import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload/{requestId}")
    public ResponseEntity<String> uploadPdf(@PathVariable int requestId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !Objects.equals(file.getContentType(), "application/pdf")) {

            return ResponseEntity.badRequest().body("Invalid file. Please upload a PDF file.");
        }

        try {
            Request request = requestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

            Pdf pdf = new Pdf();
            pdf.setRequestId(requestId);
            pdf.setPdfContent(file.getBytes());
            pdfRepository.save(pdf);
            request.setPdfExists(true);
            requestRepository.save(request);

            return ResponseEntity.ok("File uploaded successfully!");


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while uploading the file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{requestId}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@PathVariable int requestId) {
        return pdfRepository.findById(requestId)
                .map(pdf -> {
                    byte[] pdfContent = pdf.getPdfContent();
                    ByteArrayResource resource = new ByteArrayResource(pdfContent);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDisposition(ContentDisposition.builder("inline").filename("request_" + requestId + ".pdf").build());
                    headers.add("Access-Control-Allow-Origin", "*");
                    headers.add("Content-Security-Policy", "frame-ancestors 'self' http://localhost:*");

                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    //create overview pdf for an procedure with all requests
    @GetMapping("/overview/{procedureId}")
    public ResponseEntity<ByteArrayResource> overview(@PathVariable int procedureId) throws IOException {
        return pdfService.createOverview(procedureId)
                .map(pdfContent -> {
                    ByteArrayResource resource = new ByteArrayResource(pdfContent);

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_PDF);
                    headers.setContentDisposition(ContentDisposition.builder("inline").filename("procedure_" + procedureId + ".pdf").build());
                    headers.add("Access-Control-Allow-Origin", "*");
                    headers.add("Content-Security-Policy", "frame-ancestors 'self' http://localhost:*");

                    return new ResponseEntity<>(resource, headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }




    }

