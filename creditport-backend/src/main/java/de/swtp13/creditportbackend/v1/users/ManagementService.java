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

    public Optional<DisplayedUser> findUser(int id) {
        return DisplayedUser.of(userRepository.findById(id));
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

        } else if (userRepository.existsById(id)) {
            user = userRepository.findById(id).orElseThrow();
        } else {
            return 404;
        }
        if (newPass.getValue() == null || newPass.getValue().isEmpty()) {
            return 400;
        }
        user.setPassword(passwordEncoder.encode(newPass.getValue()));
        userRepository.save(user);
        return 204;
    }

    public int updateUsername(int id, UpdateRequest newUsername) {
        if (userRepository.existsById(id)) {
            var user = userRepository.findById(id).orElseThrow();
            if (newUsername.getValue() == null || newUsername.getValue().isEmpty()) {
                return 400;
            }
            user.setUsername(newUsername.getValue());
            userRepository.save(user);
            return 204;
        } else {
            return 404;
        }
    }

    public int updateRole(int id, UpdateRequest newRole) {
        if (userRepository.existsById(id)) {
            var user = userRepository.findById(id).orElseThrow();
            if (newRole.getValue() == null || newRole.getValue().isEmpty()) {
                return 400;
            }
            try {
                user.setRole(Role.valueOf(newRole.getValue()));
            } catch (IllegalArgumentException iae) {
                return 422;
            }
            userRepository.save(user);
            return 204;
        } else {
            return 404;
        }
    }

    public int register(RegisterRequest request) {
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


}
