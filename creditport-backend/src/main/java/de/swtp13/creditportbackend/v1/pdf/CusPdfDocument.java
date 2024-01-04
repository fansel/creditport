package de.swtp13.creditportbackend.v1.pdf;

import lombok.Getter;

@Getter
public class CusPdfDocument {
    private byte[] pdfContent;

    // Constructor, getters, and setters
    public CusPdfDocument(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }

    public void setPdfContent(byte[] pdfContent) {
        this.pdfContent = pdfContent;
    }
}


