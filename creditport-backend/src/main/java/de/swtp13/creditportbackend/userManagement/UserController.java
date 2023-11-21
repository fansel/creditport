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

    @GetMapping("/{id}")
    public ResponseEntity<DisplayedUser> getUser(
            @PathVariable int id
    ) {
        return managementService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(
            @PathVariable int id
    ) {
        if (managementService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(
            @RequestParam(required = false) Integer id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody String newPass
    ) {
        String jwt = token.substring(7);
        String result = managementService.updatePassword(id, jwt, newPass);
        if (result == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }

    }

    @PatchMapping("/update/username/{id}")
    public ResponseEntity updateUsername(
            @PathVariable int id,
            @RequestBody String newUsername
    ) {
        if (managementService.updateUsername(id, newUsername)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/update/role/{id}")
    public ResponseEntity updateRole(
            @PathVariable int id,
            @RequestBody String newRole
    ) {
        return ResponseEntity.status(managementService.updateRole(id, newRole)).build();
    }
}
