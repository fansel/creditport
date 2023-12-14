package de.swtp13.creditportbackend.v1.pdf;

import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private RequestRepository requestRepository;

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

                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .header("Content-Disposition", "inline; filename=\"request_" + requestId + ".pdf\"")
                            .body(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    }
}
