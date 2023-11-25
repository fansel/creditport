package de.swtp13.creditportbackend.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayedUser {
    private int userId;
    private String username;
    private String role;

    public static DisplayedUser of(User user) {
        return new DisplayedUser(user.getUserId(), user.getUsername(), user.getRole().name());
    }

    public static Optional<DisplayedUser> of(Optional<User> user) {
        if (user.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(DisplayedUser.of(user.orElseThrow()));
        }
    }

}
