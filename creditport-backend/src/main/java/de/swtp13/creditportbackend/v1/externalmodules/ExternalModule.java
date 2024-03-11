package de.swtp13.creditportbackend.v1.externalmodules;


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
public class ExternalModule {

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

    @Column(
            name = "module_description",
            nullable = false,
            columnDefinition = "VARCHAR"
    )
    private String moduleDescription;
    @ManyToOne()
    @JoinColumn(
            name = "uni_id",
            nullable = false
    )
    private University university;
    @Column(
            name = "credit_points",
            columnDefinition = "DOUBLE PRECISION",
            nullable = false
    )
    private double creditPoints;
    public ExternalModule(String number,
                          String moduleName,
                          String moduleDescription, University university, double creditPoints ){
        this.moduleDescription=moduleDescription;
        this.moduleNumber=number;
        this.moduleName= moduleName;
        this.university = university;
        this.creditPoints = creditPoints;
    }

}
