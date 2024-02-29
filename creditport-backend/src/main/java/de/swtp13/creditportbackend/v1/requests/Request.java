package de.swtp13.creditportbackend.v1.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
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
    @ManyToOne()
    @JoinColumn(
            name = "external_module_id",
            nullable = false
    )
    private ExternalModule externalModule;
    @ManyToOne()
    @JoinColumn(
            name = "internal_module_id",
            nullable = false
    )
    private InternalModule internalModule;

    @Column(
            name = "annotation_student",
            columnDefinition = "TEXT"
    )
    private String annotationStudent;

    @Column(
            name = "annotation_committee",
            columnDefinition = "TEXT"
    )
    private String annotationCommittee;

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
    private StatusRequest statusRequest;

    @Column(
            name = "created_at",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @Column(name = "pdf_exists", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean pdfExists;

    @Column(name = "module_link", nullable = true)
    private String moduleLink;





    // Überarbeiteter Konstruktor mit 'createdAt'-Parameter
    public Request(Procedure procedure, ExternalModule externalModule, InternalModule internalModule, String annotationCommittee, String annotationStudent, int creditPoints, Instant createdAt) {
        this.procedure = procedure;
        this.externalModule = externalModule;
        this.internalModule = internalModule;
        this.annotationStudent = annotationStudent;
        this.annotationCommittee = annotationCommittee;
        this.creditPoints = creditPoints;
        this.createdAt = createdAt;
        this.statusRequest = StatusRequest.NICHT_BEARBEITET;
    }

    public Request(Procedure procedure, ExternalModule externalModule, InternalModule internalModule, String annotationStudent, String annotationCommittee, int creditPoints) {
        this.procedure = procedure;
        this.externalModule = externalModule;
        this.internalModule = internalModule;
        this.annotationStudent = annotationStudent;
        this.annotationCommittee = annotationCommittee;
        this.creditPoints = creditPoints;
        this.statusRequest = StatusRequest.NICHT_BEARBEITET;
        this.createdAt = Instant.now();
    }
}
