package de.swtp13.creditportbackend.v1.procedures;

import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import de.swtp13.creditportbackend.v1.procedures.util.IDGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            name = "procedure_id",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String procedureId = IDGenerator.generateID(); // Automatische Generierung einer eindeutigen ID bei Erstellung eines Procedure-Objekts
    @Column(
            name = "status",
            columnDefinition = "VARCHAR",
            // Zugehöriger Status muss noch festgelegt werden, z.B. nicht geoeffnet, goeffnet, bearbeitet, abgelehnt, angenommen, Nachfrage noetig
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
    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "procedure",
            cascade = CascadeType.ALL)
    private List<Request> requests;

    @Column(
            name = "created_at",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    //@OneToMany(
    //        mappedBy = "procedure"
    //)
    //private Set<Request> requests;


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
    }

    public Procedure(String university, String courseName) {
        this.university = university;
        this.courseName = courseName;
        this.status = Status.NEU;
        this.createdAt = LocalDateTime.now();
    }

    public List<Integer> getRequestIds() {
        List<Integer> RequestIds = new ArrayList<>();
        for (Request request: requests) {
            RequestIds.add(request.getRequestId());
        }
        return RequestIds;
    }
    /*

{
  "university": "String",
  "course" : "String",
  "requests" : [
    {
      "externalModules" :
      [
        {
          "lp" : "Int",
          "name" : "String",
          "urlModulDescription" : "String",
          "fileModulDescription" : "File",
        },

      ],
      "internalModules" : [

      ]
    },
  ],
}





 */
}