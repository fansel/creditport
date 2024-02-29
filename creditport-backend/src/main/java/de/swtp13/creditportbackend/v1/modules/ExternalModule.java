package de.swtp13.creditportbackend.v1.modules;


import de.swtp13.creditportbackend.v1.universities.University;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * @author Felix
 * Diese Klasse repräsentiert ein Modul.
 */
@Entity(name = "ExternalModules")
@Table(name = "external_modules")

@Data // Erstellt Getter, Setter und toString automatisch
@NoArgsConstructor // Erstellt den Standardkonstruktor
@AllArgsConstructor // Erstellt einen Konstruktor mit allen Feldern als Parameter
public class ExternalModule extends Module{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "module_id", updatable = false, nullable = false)
    private UUID moduleId;
    @Column(
            name = "module_number",
            columnDefinition = "VARCHAR",
            nullable = true
    )
    private String moduleNumber;
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

    public ExternalModule(String number,
                          String moduleName,
                          String moduleDescription, University university ){
        this.moduleDescription=moduleDescription;
        this.moduleNumber=number;
        this.moduleName= moduleName;
        this.university = university;
        //später: als uni leipzig initialisieren
    }

}
