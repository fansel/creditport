package de.swtp13.creditportbackend.v1.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private String role;

    public static UserDTO of(User user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getRole().name());
    }

    public static Optional<UserDTO> of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<User> user) {
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(UserDTO.of(user.orElseThrow()));
        }
    }

}
