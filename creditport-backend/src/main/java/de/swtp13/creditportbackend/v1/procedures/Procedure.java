package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;
import de.swtp13.creditportbackend.v1.requests.Request;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.swtp13.creditportbackend.v1.universities.University;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Procedures")
@Table(name = "procedures")
@Data
@NoArgsConstructor // Lombok generiert einen Konstruktor ohne Parameter
@AllArgsConstructor
/**
 * Die Procedure Klasse repräsentiert ein Vorgang. Der Vorgang enthält mehrere Anträge.
 */
public class Procedure {

    @Getter
    @Id
    @GenericGenerator(
            name = "id-generator",
            type = IDGenerator.class
    )
    @GeneratedValue(generator = "id-generator")
    @Column(
            name = "procedure_id",
            columnDefinition = "INT",
            nullable = false
    )
    private int procedureId;
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
    @ManyToOne
    @JoinColumn(
            name = "uni_id",
            nullable = false
    )
    private University university;
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
    private Instant createdAt;

    @Column(
            name = "last_updated_on",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Instant lastUpdated;

    @ManyToMany(mappedBy = "procedures")
    private List<Request> requests;

    // Benutzerdefinierter Konstruktor ohne ID


    public Procedure(String annotation, University university, String courseName) {
        this.annotation = annotation;
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt = Instant.now();
        this.lastUpdated = this.createdAt;
    }
    public Procedure(String annotation, University university, String courseName, List<Request> requests) {
        this.annotation = annotation;
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt = Instant.now();
        this.lastUpdated = this.createdAt;
        this.requests = requests;
    }

    public Procedure(University university, String courseName, List<Request> requests) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt =Instant.now();
        this.lastUpdated = this.createdAt;
        this.requests = requests;
    }
    public Procedure(University university, String courseName) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt =Instant.now();
        this.lastUpdated = this.createdAt;
    }
}
