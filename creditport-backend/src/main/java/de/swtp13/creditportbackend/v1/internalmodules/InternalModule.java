package de.swtp13.creditportbackend.v1.internalmodules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.swtp13.creditportbackend.v1.courses.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
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
public class InternalModule {

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
    @Column(
           name = "module_description",
            nullable = false,
           columnDefinition = "VARCHAR"
    )
    private String moduleDescription;
    @Column(
            name = "credit_points",
            columnDefinition = "INT",
            nullable = false
    )
    private int creditPoints;

    @ManyToMany(mappedBy = "internalModules")
    private List<Course> courses;


    public InternalModule(String number, String moduleName, String moduleDescription, int creditPoints){
        this.moduleDescription = moduleDescription;
        this.moduleName = moduleName;
        this.number = number;
        this.creditPoints = creditPoints;
    }
    public InternalModule(String number, String moduleName, String moduleDescription, int creditPoints, List<Course> courses){
        this.moduleDescription = moduleDescription;
        this.moduleName = moduleName;
        this.number = number;
        this.creditPoints = creditPoints;
        this.courses=courses;
    }
}
