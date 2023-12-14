package de.swtp13.creditportbackend.v1.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final ManagementService managementService;

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        System.out.println("Get all Users");
        ArrayList<User> userlist = new ArrayList<>(userRepository.findAll());
        ArrayList<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        for (User user:
             userlist) {
            userResponseDTOList.add(UserResponseDTO.of(user));
        }
        return userResponseDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(
            @PathVariable int id
    ) {
        return managementService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id
    ) {
        return userRepository.findById(id)
                .map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.status(managementService.register(request))
                .body(
                        UserResponseDTO.of(
                                userRepository.findByUsername(request.getUsername()).orElseThrow()
                        )
                );
    }

    @PatchMapping("/update/password")
    public ResponseEntity<?> updatePassword(
            @RequestParam(required = false) Integer id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdateRequest newPass
    ) {
        String jwt = token.substring(7);
        return ResponseEntity.status(managementService.updatePassword(id, jwt, newPass)).build();
    }

    @PatchMapping("/update/username")
    public ResponseEntity<?> updateUsername(
            @RequestParam int id,
            @RequestBody UpdateRequest newUsername
    ) {
        return ResponseEntity.status(managementService.updateUsername(id, newUsername)).build();
    }


    @PatchMapping("/update/role")
    public ResponseEntity<?> updateRole(
            @RequestParam int id,
            @RequestBody UpdateRequest newRole
    ) {
        return ResponseEntity.status(managementService.updateRole(id, newRole)).build();
    }
}
