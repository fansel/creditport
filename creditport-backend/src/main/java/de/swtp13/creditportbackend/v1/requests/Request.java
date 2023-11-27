package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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

    //private File pdf
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
    private LocalDateTime createdAt;
    //@Column(
    //      name = "PDFinByte",
    //    columnDefinition = "INT",
    //  nullable = false
    //)

    public Request(Procedure procedure, String externalModuleId, String internalModuleId, String annotation, int creditPoints) {
        this.procedure = procedure;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.annotation = annotation;
        this.creditPoints = creditPoints;
        this.status = Status.NICHT_BEARBEITET;
        this.createdAt = LocalDateTime.now();
    }

    public Request(Procedure procedure, String externalModuleId, String internalModuleId, String annotation, int creditPoints, LocalDateTime createdAt) {
        this.procedure = procedure;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.annotation = annotation;
        this.creditPoints = creditPoints;
        this.createdAt = createdAt;
        this.status = Status.NICHT_BEARBEITET;

    }

}
