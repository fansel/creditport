package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;
import de.swtp13.creditportbackend.v1.requests.Request;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            name = "id",
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
   /* @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "INT"
    )
    private long createdAt;

    @Column(
            name = "last_updated_on",
            nullable = false,
            columnDefinition = "INT"
    )
    private long lastUpdated;

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
        Date now = new Date();
        this.createdAt = now.getTime();
        this.lastUpdated = this.createdAt;
    }

    public Procedure(String university, String courseName) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        Date now = new Date();
        this.createdAt = now.getTime();
        this.lastUpdated = this.createdAt;
    }*/
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
        this.createdAt = Instant.now();
        this.lastUpdated = this.createdAt;
    }

    public Procedure(String university, String courseName) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt =Instant.now();
        this.lastUpdated = this.createdAt;
    }
}
