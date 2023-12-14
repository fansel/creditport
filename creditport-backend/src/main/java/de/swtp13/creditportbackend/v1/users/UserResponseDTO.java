package de.swtp13.creditportbackend.v1.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private int userId;
    private String username;
    private String role;

    public static UserResponseDTO of(User user) {
        return new UserResponseDTO(user.getUserId(), user.getUsername(), user.getRole().name());
    }

    public static Optional<UserResponseDTO> of(Optional<User> user) {
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(UserResponseDTO.of(user.orElseThrow()));
        }
    }

}
