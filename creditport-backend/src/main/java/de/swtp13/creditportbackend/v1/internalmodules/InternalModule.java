package de.swtp13.creditportbackend.v1.internalmodules;

import de.swtp13.creditportbackend.v1.modules.Module;
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
@Entity(name = "InternalModules")
@Table(name = "internal_modules")

@Data // Erstellt Getter, Setter und toString automatisch
@NoArgsConstructor // Erstellt den Standardkonstruktor
@AllArgsConstructor // Erstellt einen Konstruktor mit allen Feldern als Parameter
public class InternalModule extends Module {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "module_id", updatable = false, nullable = false)
    private UUID moduleId;
    @Column(
            name= "number",
            columnDefinition = "VARCHAR",
            nullable = false
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





}
