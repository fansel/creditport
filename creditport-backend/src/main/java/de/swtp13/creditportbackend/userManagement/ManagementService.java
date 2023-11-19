package de.swtp13.creditportbackend.userManagement;

import de.swtp13.creditportbackend.config.JwtService;
import de.swtp13.creditportbackend.users.Role;
import de.swtp13.creditportbackend.users.User;
import de.swtp13.creditportbackend.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UpdatePasswordResponse updatePassword(UpdateRequest request, String token) {
        User user;
        if (request.getId() == null) {
            try {
                user = userRepository.findById(
                        userRepository.findByUsername(
                                jwtService.extractUsername(token)
                        ).orElseThrow().getUserId()
                ).orElseThrow();
            } catch (IllegalArgumentException iae) {
                return UpdatePasswordResponse.builder()
                        .success(false)
                        .errorMsg("User could not be found")
                        .build();
            }

        } else if (userRepository.existsById(request.getId())) {
            user = userRepository.findById(request.getId()).orElseThrow();
        } else {
            return UpdatePasswordResponse.builder()
                    .success(false)
                    .errorMsg("User could not be found")
                    .build();
        }
        user.setPassword(passwordEncoder.encode(request.getUpdatedValue()));
        userRepository.save(user);
        return UpdatePasswordResponse.builder()
                .success(true)
                .token(jwtService.generateToken(user))
                .build();
    }

    public ManagementResponse updateUsername(UpdateRequest request) {
        if (userRepository.existsById(request.getId())) {
            var user = userRepository.findById(request.getId()).orElseThrow();
            user.setUsername(request.getUpdatedValue());
            userRepository.save(user);
            return ManagementResponse.builder()
                    .success(true)
                    .build();
        } else {
            return ManagementResponse.builder()
                    .success(false)
                    .errorMsg("User could not be found")
                    .build();
        }
    }

    public ManagementResponse updateRole(UpdateRequest request) {
        if (userRepository.existsById(request.getId())) {
            var user = userRepository.findById(request.getId()).orElseThrow();
            try {
                user.setRole(Role.valueOf(request.getUpdatedValue()));
            } catch (IllegalArgumentException iae) {
                return ManagementResponse.builder()
                        .success(false)
                        .errorMsg("Invalid role")
                        .build();
            }
            userRepository.save(user);
            return ManagementResponse.builder()
                    .success(true)
                    .build();
        } else {
            return ManagementResponse.builder()
                    .success(false)
                    .errorMsg("User could not be found")
                    .build();
        }
    }

    public ManagementResponse deleteUser(UserRequest request) {
        // only ID is specified
        if (request.getId() != null && request.getUsername() == null) {
            return deleteUserById(request.getId());

        //only username is specified
        } else if (request.getId() == null && request.getUsername() != null) {
            return deleteUserByUsername(request.getUsername());

        // both username and ID are specified
        } else if (request.getId() != null && request.getUsername() != null) {
            if (userRepository.findByUsername(request.getUsername())
                    .equals(userRepository.findById(request.getId()))) {
                return deleteUserById(request.getId());
            } else {
                return ManagementResponse.builder()
                        .success(false)
                        .errorMsg("Username and UserID contradict")
                        .build();
            }

        // neither ID nor username are specified
        } else {
            return ManagementResponse.builder()
                    .success(false)
                    .errorMsg("No user specified")
                    .build();
        }
    }

    private ManagementResponse deleteUserById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ManagementResponse.builder()
                    .success(true)
                    .build();
        } else {
            return ManagementResponse.builder()
                    .success(false)
                    .errorMsg("User could not be found")
                    .build();
        }
    }

    private ManagementResponse deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.delete(user.orElseThrow());
            return ManagementResponse.builder()
                    .success(true)
                    .build();
        } else {
            return ManagementResponse.builder()
                    .success(false)
                    .errorMsg("User could not be found")
                    .build();
        }
    }


}
