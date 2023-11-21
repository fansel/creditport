package de.swtp13.creditportbackend.userManagement;

import de.swtp13.creditportbackend.users.User;
import de.swtp13.creditportbackend.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/usermanagement")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final ManagementService managementService;

    @GetMapping("/users")
    public List<DisplayedUser> getAllUsers() {
        System.out.println("Get all Users");
        ArrayList<User> userlist = new ArrayList<>(userRepository.findAll());
        ArrayList<DisplayedUser> displayedUserList = new ArrayList<>();
        for (User user:
             userlist) {
            displayedUserList.add(DisplayedUser.of(user));
        }
        return displayedUserList;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(managementService.findUser(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ManagementResponse> deleteUser(
            @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(managementService.deleteUser(request));
    }

    @PatchMapping("/update/password")
    public ResponseEntity<UpdatePasswordResponse> updatePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdateRequest request
    ) {
        String jwt = token.substring(7);
        return ResponseEntity.ok(managementService.updatePassword(request, jwt));
    }

    @PatchMapping("/update/username")
    public ResponseEntity<ManagementResponse> updateUsername(
            @RequestBody UpdateRequest request
    ) {
        return ResponseEntity.ok(managementService.updateUsername(request));
    }

    @PatchMapping("/update/role")
    public ResponseEntity<ManagementResponse> updateRole(
            @RequestBody UpdateRequest request
    ) {
        return ResponseEntity.ok(managementService.updateRole(request));
    }
}
