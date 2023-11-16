package de.swtp13.creditportbackend.universities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Universities")
@Table(name = "universities")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(name = "uni_id", columnDefinition = "INT")
    private int uniId;

    @Column(name = "uni_name", columnDefinition = "VARCHAR", nullable = false)
    private String uniName;

    public University(String uniName) {
        this.uniName = uniName;
    }
}
