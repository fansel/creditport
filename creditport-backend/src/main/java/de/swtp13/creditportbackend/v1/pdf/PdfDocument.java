package de.swtp13.creditportbackend.v1.pdf;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
public class PdfDocument {
    private byte[] pdfContent;

    // Constructor, getters, and setters
    public PdfDocument(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }

    public void setPdfContent(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }
}


