package de.swtp13.creditportbackend.universities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(
        name = "Universities"
)
@Table(
        name = "universities"
)
public class University {
    @Column(
            name = "uni_id",
            columnDefinition = "INT"
    )
    @Id
    @GeneratedValue
    private int uniId;

    @Column(
            name = "uni_name",
            columnDefinition = "VARCHAR",
            nullable = false
    )
    private String uniName;

    public University(String s) {
    }
}
