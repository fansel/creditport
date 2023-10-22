package de.swtp13.creditportbackend.procedures;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    // Benutzerdefinierter Konstruktor ohne ID
    public Procedure(int status, String annotation) {
        this.status = status;
        this.annotation = annotation;
    }
}
