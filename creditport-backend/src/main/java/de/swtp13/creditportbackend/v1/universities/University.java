package de.swtp13.creditportbackend.v1.universities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * Die Klasse stellt eine Universität dar.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Universities")
@Table(name = "universities")
public class University {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uni_id", updatable = false, nullable = false)
    private UUID uniId;

    @Column(name = "uni_name", columnDefinition = "VARCHAR", nullable = false, unique = true)
    private String uniName;


    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVerified;


    public University(String uniName) {
        this.uniName = uniName;
    }
}
