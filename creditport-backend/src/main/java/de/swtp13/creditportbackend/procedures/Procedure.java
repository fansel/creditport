package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.modules.Module;
import de.swtp13.creditportbackend.request.Request;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.swtp13.creditportbackend.procedures.util.IDGenerator;

@Entity(name = "Procedures")
@Table(name = "procedures")
@Data
@NoArgsConstructor // Lombok generiert einen Konstruktor ohne Parameter
public class Procedure {

    @Id
    @Column(
            name = "procedureId",
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
            columnDefinition = "VARCHAR"
    )
    private String university;
    @Column(
            name = "courseName",
            columnDefinition = "VARCHAR"
    )
    private String courseName;

    @OneToMany(
            mappedBy = "procedure"
    )
    private Set<Request> requests;



    // Benutzerdefinierter Konstruktor ohne ID
    public Procedure(int status, String annotation) {
        this.status = status;
        this.annotation = annotation;
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
