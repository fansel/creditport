package de.swtp13.creditportbackend.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Felix
 * Diese Klasse repräsentiert ein Modul.
 */
@Entity
@Table(name = "modules")
@Data // Erstellt Getter, Setter und toString automatisch
@NoArgsConstructor // Erstellt den Standardkonstruktor
@AllArgsConstructor // Erstellt einen Konstruktor mit allen Feldern als Parameter
public class Module {

    @Id
    private String number;
    private String name;
    private String description;
}
