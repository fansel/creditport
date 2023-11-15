package de.swtp13.creditportbackend.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Users")
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
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
            columnDefinition = "VARCHAR",
            nullable = true
    )
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
