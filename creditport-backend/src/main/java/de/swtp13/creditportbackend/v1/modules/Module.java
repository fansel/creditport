package de.swtp13.creditportbackend.v1.modules;

import de.swtp13.creditportbackend.v1.universities.University;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Felix
 * Diese Klasse repräsentiert ein Modul.
 */
@Entity(name = "Modules")
@Table(name = "modules")

@Data // Erstellt Getter, Setter und toString automatisch
@NoArgsConstructor // Erstellt den Standardkonstruktor
@AllArgsConstructor // Erstellt einen Konstruktor mit allen Feldern als Parameter
public class Module {

    @Id
    @Column(
            name = "number",
            columnDefinition = "VARCHAR"
    )
    private String number;
    @Column(
            name = "module_name",
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String moduleName;
    //@Column(
      //      name = "creditPoints",
        //    nullable = false,
          //  columnDefinition = "INT"
    //)
    //private int creditpoints;
    @Column(
           name = "module_description",
            nullable = false,
           columnDefinition = "VARCHAR"
    )
    private String moduleDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "uni_id"
    )
    private University university;

    public Module(String number,
                  String moduleName,
                  String moduleDescription){
        this.moduleDescription=moduleDescription;
        this.number=number;
        this.moduleName= moduleName;
        //später: als uni leipzig initialisieren
    }

}
