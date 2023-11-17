package de.swtp13.creditportbackend.procedures;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import de.swtp13.creditportbackend.procedures.util.IDGenerator;

@Entity(name = "Procedures")
@Table(name = "procedures")
@Data
@NoArgsConstructor // Lombok generiert einen Konstruktor ohne Parameter
@AllArgsConstructor
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
            columnDefinition = "INT",
            // Zugehöriger Status muss noch festgelegt werden, z.B. nicht geoeffnet, goeffnet, bearbeitet, abgelehnt, angenommen, Nachfrage noetig
            nullable = false
    )
    private int status;
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

    //@OneToMany(
    //        mappedBy = "procedure"
    //)
    //private Set<Request> requests;



    // Benutzerdefinierter Konstruktor ohne ID
    public Procedure(int status, String annotation) {
        this.university = "Universität Leipzig";
        this.courseName = "Informatik Bachelor";
        this.status = status;
        this.annotation = annotation;
    }
    public Procedure(String annotation,String university,String courseName ){
        this.annotation = annotation;
        this.university = university;
        this.courseName = courseName;
        this.status = 1;
    }
    public Procedure(String university,String courseName ){
        this.university = university;
        this.courseName = courseName;
        this.status = 1;
    }
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
