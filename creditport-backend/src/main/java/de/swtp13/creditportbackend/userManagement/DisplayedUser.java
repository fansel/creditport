package de.swtp13.creditportbackend.userManagement;

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

}
