package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;
import de.swtp13.creditportbackend.v1.requests.Request;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Procedures")
@Table(name = "procedures")
@Data
@NoArgsConstructor // Lombok generiert einen Konstruktor ohne Parameter
@AllArgsConstructor
/**
 * Die Procedure Klasse repräsentiert ein Vorgang. Der Vorgang enthält mehrere Anträge.
 */
public class Procedure {

    @Id
    @Column(
            name = "id",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String procedureId = IDGenerator.generateID(); // Automatische Generierung einer eindeutigen ID bei Erstellung eines Procedure-Objekts
    @Column(
            name = "status",
            columnDefinition = "VARCHAR",
            // Zugehöriger Status muss noch festgelegt werden, z.B. nicht geoeffnet, geoeffnet, bearbeitet, abgelehnt, angenommen, Nachfrage noetig
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(
            name = "annotation",
            columnDefinition = "TEXT"
    )
    private String annotation;
    @Column(
            name = "university",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String university;
    @Column(
            name = "course_name",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String courseName;
    @Column(
            name = "created_at",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(
            name = "last_updated_on",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdated;

    @Getter
    @Transient
    private List<Request> requests = new ArrayList<>();

    // Benutzerdefinierter Konstruktor ohne ID
    public Procedure(Status status, String annotation) {
        this.university = "Universität Leipzig";
        this.courseName = "Informatik Bachelor";
        this.status = status;
        this.annotation = annotation;
    }

    public Procedure(String annotation, String university, String courseName) {
        this.annotation = annotation;
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = this.createdAt;
    }

    public Procedure(String university, String courseName) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = this.createdAt;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}