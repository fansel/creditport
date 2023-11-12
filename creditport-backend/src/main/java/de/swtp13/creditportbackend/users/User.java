package de.swtp13.creditportbackend.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Users")
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(
            name = "user_id",
            columnDefinition = "INT",
            nullable = false
    )
    private int userId;
    @Column(
            name = "username",
            columnDefinition = "VARCHAR",
            unique = true,
            nullable = false
    )
    private String username;
    @Column(
            name = "password",
            columnDefinition = "VARCHAR",
            unique = false,
            nullable = false
    )
    private String password;
    @Column(
            name = "role",
            columnDefinition = "INT",
            nullable = false
    )
    private int role;
}
