package de.swtp13.creditportbackend.v1.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.swtp13.creditportbackend.v1.procedures.Procedure;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Die Klasse repräsentiert einen Antrag.
 */
@Entity(name = "Requests")
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue
    @Column(
            name = "request_id",
            columnDefinition = "INT"
    )
    private int requestId;

    @ManyToOne()
    @JoinColumn(
            name = "procedure_id",
            nullable = false
    )
    @JsonIgnore
    private Procedure procedure;

    @Column(
            name = "external_module_id",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String externalModuleId;

    @Column(
            name = "internal_module_id",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String internalModuleId;

    @Column(
            name = "annotation",
            columnDefinition = "TEXT"
    )
    private String annotation;

    @Column(
            name = "credit_points",
            columnDefinition = "INT",
            nullable = false
    )
    private int creditPoints;

    @Column(
            name = "request_status",
            columnDefinition = "VARCHAR",
            nullable = false
    )

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(
            name = "created_at",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Lob
    @Column(name = "pdf_content",
    nullable = true)
    private byte[] pdfContent;

    @Column(name = "module_link", nullable = true)
    private String moduleLink;




    // Überarbeiteter Konstruktor
    public Request(Procedure procedure, String externalModuleId, String internalModuleId, String annotation, int creditPoints, byte[] pdfContent) {
        this.procedure = procedure;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.annotation = annotation;
        this.creditPoints = creditPoints;
        this.status = Status.NICHT_BEARBEITET;
        this.createdAt = Instant.now();
        this.pdfContent = pdfContent;
    }

    // Überarbeiteter Konstruktor mit 'createdAt'-Parameter
    public Request(Procedure procedure, String externalModuleId, String internalModuleId, String annotation, int creditPoints, Instant createdAt, byte[] pdfContent) {
        this.procedure = procedure;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.annotation = annotation;
        this.creditPoints = creditPoints;
        this.createdAt = createdAt;
        this.status = Status.NICHT_BEARBEITET;
        this.pdfContent = pdfContent;
    }

    public Request(Procedure procedure, String externalModuleId, String internalModuleId, String annotation, int creditPoints) {
        this.procedure = procedure;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.annotation = annotation;
        this.creditPoints = creditPoints;
        this.status = Status.NICHT_BEARBEITET;
        this.createdAt = Instant.now();
        this.pdfContent = null; // oder ein Standard-PDF-Byte-Array, falls gewünscht
    }
}
