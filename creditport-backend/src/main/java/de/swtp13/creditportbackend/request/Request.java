package de.swtp13.creditportbackend.request;

import de.swtp13.creditportbackend.procedures.Procedure;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Requests")
@Table(name = "requests")
@Data
@NoArgsConstructor
public class Request {
    @Id
    @Column(
            name = "requestId",
            columnDefinition = "TEXT"
    )
    private String requestId;
    @ManyToOne()
    @JoinColumn(
            name="procedureId",
            nullable=false
    )
    private Procedure procedure;
    @Column(
            name = "externalModuleId",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String externalModuleId;
    @Column(
            name = "internalModuleId",
            columnDefinition = "TEXT",
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
            name = "creditPoints",
            columnDefinition = "INT",
            nullable = false
    )
    private int creditPoints;


}
