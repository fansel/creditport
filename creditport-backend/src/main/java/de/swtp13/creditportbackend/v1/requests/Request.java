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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @ManyToMany
    @JoinTable(
            name = "request_procedure",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private List<Procedure> procedures;

    @ManyToMany()
    @JoinTable(
            name = "extmod_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "external_module_id" )
    )
    private List<ExternalModule> externalModules;
    @ManyToMany()
    @JoinTable(
            name = "intmod_request",
            joinColumns = @JoinColumn(name = "request_id"),
            inverseJoinColumns = @JoinColumn(name = "internal_module_id" )
    )
    private List<InternalModule> internalModules;

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

    public List<UUID> getInternalModuleIds(){
        List<UUID> internalModuleIds = new ArrayList<>();
        for (InternalModule internalModule: internalModules){
            internalModuleIds.add(internalModule.getModuleId());
        }
        return internalModuleIds;
    }
    public List<UUID> getExternalModuleIds(){
        List<UUID> externalModuleIds = new ArrayList<>();
        for (ExternalModule externalModule: externalModules){
            externalModuleIds.add(externalModule.getModuleId());
        }
        return externalModuleIds;
    }
    public List<Integer> getProcedureIds(){
        List<Integer> procedureIds = new ArrayList<>();
        for (Procedure procedure: procedures){
            procedureIds.add(procedure.getProcedureId());
        }
        return procedureIds;
    }

    // Überarbeiteter Konstruktor mit 'createdAt'-Parameter
    public Request(List<Procedure> procedures, List<ExternalModule> externalModules, List<InternalModule> internalModules, String annotationCommittee, String annotationStudent, int creditPoints, Instant createdAt) {
        this.procedures = procedures;
        this.externalModules = externalModules;
        this.internalModules = internalModules;
        this.annotationStudent = annotationStudent;
        this.annotationCommittee = annotationCommittee;
        this.creditPoints = creditPoints;
        this.createdAt = createdAt;
        this.statusRequest = StatusRequest.NICHT_BEARBEITET;
    }

    public Request(List<Procedure> procedures, List<ExternalModule> externalModules, List<InternalModule> internalModules, String annotationStudent, String annotationCommittee, int creditPoints) {
        this.procedures = procedures;
        this.externalModules = externalModules;
        this.internalModules = internalModules;
        this.annotationStudent = annotationStudent;
        this.annotationCommittee = annotationCommittee;
        this.creditPoints = creditPoints;
        this.statusRequest = StatusRequest.NICHT_BEARBEITET;
        this.createdAt = Instant.now();
    }
}
