package de.swtp13.creditportbackend.requests;

import de.swtp13.creditportbackend.procedures.Procedure;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Requests")
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @Column(
            name = "request_id",
            columnDefinition = "VARCHAR"
    )
    private String requestId;
    @ManyToOne()
    @JoinColumn(
            name="procedure_id",
            nullable=false
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
            columnDefinition = "TEXT",
            nullable = true
    )
    private String annotation;
    @Column(
            name = "credit_points",
            columnDefinition = "INT",
            nullable = false
    )
    private int creditPoints;


}
