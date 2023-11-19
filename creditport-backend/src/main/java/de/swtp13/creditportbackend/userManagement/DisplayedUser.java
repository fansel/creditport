package de.swtp13.creditportbackend.userManagement;

import de.swtp13.creditportbackend.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
