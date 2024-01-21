package de.swtp13.creditportbackend.v1.users;

import de.swtp13.creditportbackend.v1.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public Optional<UserDTO> findUser(int id) {
        return UserDTO.of(userRepository.findById(id));
    }

    public int updatePassword(Integer id, String token, UpdateRequest newPass) {
        User user;
        if (id == null) {
            try {
                user = userRepository.findById(
                        userRepository.findByUsername(
                                jwtService.extractUsername(token)
                        ).orElseThrow().getUserId()
                ).orElseThrow();
            } catch (NoSuchElementException nsee) {
                return 404;
            }

        } else if (jwtService.extractRole(token).equals("ADMIN")) {
            if (userRepository.existsById(id)) {
                user = userRepository.findById(id).orElseThrow();
            } else {
                return 404;
            }
        } else {
            return 403;
        }
        if (newPass.getValue() == null || newPass.getValue().isEmpty()) {
            return 400;
        }
        user.setPassword(passwordEncoder.encode(newPass.getValue()));
        userRepository.save(user);
        return 204;
    }

    public int updateUser(UserDTO updatedUser) {
        int id = updatedUser.getUserId();
        if (userRepository.existsById(id)) {
            var user = userRepository.findById(id).orElseThrow();
            if (updatedUser.getUsername() == null || updatedUser.getUsername().isEmpty()) {
                return 400;
            }
            user.setUsername(updatedUser.getUsername());
            try {
                user.setRole(Role.valueOf(updatedUser.getRole()));
            } catch (IllegalArgumentException iae) {
                return 422;
            }
            userRepository.save(user);
            return 204;
        } else {
            return 404;
        }
    }

    public int register(RegisterRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return 409;
        }
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);
        return 200;
    }


    public boolean isAuthorized(String token) {
        if (token == null || token.length() < 7) {
            return false;
        }
        String jwt = token.substring(7);
        if (jwt.trim().isEmpty()) {
            return false;
        }
        try {
            String username = jwtService.extractUsername(jwt);
            return userRepository.findByUsername(username)
                    .map(user -> jwtService.isTokenValid(jwt, user))
                    .orElse(false);
        } catch (Exception e) {
            return false;
        }
    }


}
