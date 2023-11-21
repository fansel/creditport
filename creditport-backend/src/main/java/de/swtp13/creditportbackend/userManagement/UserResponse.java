package de.swtp13.creditportbackend.userManagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private boolean success;
    private String errorMsg;
    private DisplayedUser user;
}
