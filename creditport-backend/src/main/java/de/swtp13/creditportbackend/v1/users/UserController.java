package de.swtp13.creditportbackend.v1.users;

import de.swtp13.creditportbackend.v1.users.dto.PasswordUpdateRequestDTO;
import de.swtp13.creditportbackend.v1.users.dto.RegisterRequestDTO;
import de.swtp13.creditportbackend.v1.users.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "returns a list of all users, only usable by admin", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
            @ApiResponse(responseCode = "403", description = "Missing admin authentification", content = @Content)
    })
    @GetMapping
    public List<UserDTO> getAllUsers() {
        System.out.println("Get all Users");
        ArrayList<User> userlist = new ArrayList<>(userRepository.findAll());
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        for (User user: userlist) {
            userDTOList.add(UserDTO.of(user));
        }
        return userDTOList;
    }

    @Operation(summary = "returns a single user, only usable by admin", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "403", description = "Missing admin authentification", content = @Content),
            @ApiResponse(responseCode = "404", description = "User id not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(
            @PathVariable int id
    ) {
        return managementService.findUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "deletes a user from the database, only usable by admin", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "403", description = "Missing admin authentification", content = @Content),
            @ApiResponse(responseCode = "404", description = "User id not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id
    ) {
        return userRepository.findById(id)
                .map(user -> {
                    if (user.getRole() == Role.ADMIN && userRepository.findAllByRole(user.getRole()).size() <= 1)
                        return ResponseEntity.status(423).build();
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "adds a user to the database, only usable by admin", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Username or password are blank or null", content = @Content),
            @ApiResponse(responseCode = "403", description = "Missing admin authentification", content = @Content),
            @ApiResponse(responseCode = "409", description = "Username already taken, returns existing user",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "422", description = "Invalid role has been given", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        HttpStatus responseStatus = managementService.register(request);
        if (responseStatus == HttpStatus.OK || responseStatus == HttpStatus.CONFLICT) {
            return ResponseEntity.status(responseStatus)
                    .body(
                            UserDTO.of(
                                    userRepository.findByUsername(request.getUsername()).orElseThrow()
                            )
                    );
        } else {
            return ResponseEntity.status(responseStatus).build();
        }

    }

    @Operation(summary = "updates password of the calling user",
            responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "400", description = "New password is null or empty",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Missing authentication",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User id not found", content = @Content)
            })
    @PostMapping("/update/password")
    public ResponseEntity<?> updatePassword(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody PasswordUpdateRequestDTO newPass
    ) {
        String jwt = token.substring(7);
        return ResponseEntity.status(managementService.updatePassword(jwt, newPass)).build();
    }

    @Operation(summary = "updates a user, only usable by admin", responses = {
            @ApiResponse(responseCode = "204", content = @Content),
            @ApiResponse(responseCode = "400", description = "New username is null or empty", content = @Content),
            @ApiResponse(responseCode = "403", description = "Missing admin authentification", content = @Content),
            @ApiResponse(responseCode = "404", description = "User id not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "New username is already taken", content = @Content),
            @ApiResponse(responseCode = "422", description = "Invalid role has been given", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable int id,
            @RequestBody User updatedUser
    ) {
        return ResponseEntity.status(managementService.updateUser(id, updatedUser)).build();
    }

}
