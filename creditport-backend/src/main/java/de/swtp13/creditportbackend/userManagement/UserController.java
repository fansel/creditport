package de.swtp13.creditportbackend.userManagement;

import de.swtp13.creditportbackend.users.User;
import de.swtp13.creditportbackend.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usermanagement")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final ManagementService managementService;

    @GetMapping("/users")
    public List<User> getAllModules() {
        System.out.println("Get all Users");
        return userRepository.findAll();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ManagementResponse> deleteUser(
            @RequestBody UserRequest request
    ) {
        ManagementResponse response = managementService.deleteUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/password")
    public ResponseEntity<UpdatePasswordResponse> updatePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdateRequest request
    ) {
        String jwt = token.substring(7);
        return ResponseEntity.ok(managementService.updatePassword(request, jwt));
    }

    @PostMapping("/update/username")
    public ResponseEntity<ManagementResponse> updateUsername(
            @RequestBody UpdateRequest request
    ) {
        return ResponseEntity.ok(managementService.updateUsername(request));
    }

    @PostMapping("/update/role")
    public ResponseEntity<ManagementResponse> updateRole(
            @RequestBody UpdateRequest request
    ) {
        return ResponseEntity.ok(managementService.updateRole(request));
    }
}
