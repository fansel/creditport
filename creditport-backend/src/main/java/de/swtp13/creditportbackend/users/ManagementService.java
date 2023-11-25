package de.swtp13.creditportbackend.users;

import de.swtp13.creditportbackend.config.JwtService;
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

    public Optional<DisplayedUser> findUser(int id) {
        return DisplayedUser.of(userRepository.findById(id));
    }

    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public String updatePassword(Integer id, String token, String newPass) {
        User user;
        if (id == null) {
            try {
                user = userRepository.findById(
                        userRepository.findByUsername(
                                jwtService.extractUsername(token)
                        ).orElseThrow().getUserId()
                ).orElseThrow();
            } catch (IllegalArgumentException iae) {
                return null;
            }

        } else if (userRepository.existsById(id)) {
            user = userRepository.findById(id).orElseThrow();
        } else {
            return null;
        }
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public boolean updateUsername(int id, String newUsername) {
        if (userRepository.existsById(id)) {
            var user = userRepository.findById(id).orElseThrow();
            user.setUsername(newUsername);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public int updateRole(int id, String newRole) {
        if (userRepository.existsById(id)) {
            var user = userRepository.findById(id).orElseThrow();
            if (newRole == null || newRole.isEmpty()) {
                return 400;
            }
            try {
                user.setRole(Role.valueOf(newRole));
            } catch (IllegalArgumentException iae) {
                return 422;
            }
            userRepository.save(user);
            return 200;
        } else {
            return 404;
        }
    }


}
