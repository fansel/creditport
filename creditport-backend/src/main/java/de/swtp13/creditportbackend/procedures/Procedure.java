package de.swtp13.creditportbackend.procedures;

import de.swtp13.creditportbackend.modules.Module;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import de.swtp13.creditportbackend.procedures.util.IDGenerator;

@Entity
@Table(name = "procedures")
@Data
@NoArgsConstructor // Lombok generiert einen Konstruktor ohne Parameter
public class Procedure {

    @Id
    private String id = IDGenerator.generateID(); // Automatische Generierung einer eindeutigen ID bei Erstellung eines Procedure-Objekts
    private int status;
    private String annotation;
    private String university;
    private String course;






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
