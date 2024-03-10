package de.swtp13.creditportbackend.v1.pdf;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Pdf")
@Table(name = "pdf")
@Data
public class Pdf {


    @Id
    @Column(name = "request_id",
            nullable = false)
    private int requestId;

    @Lob
    @Column(name = "pdf_content",
            nullable = true)
    private byte[] pdfContent;



}
