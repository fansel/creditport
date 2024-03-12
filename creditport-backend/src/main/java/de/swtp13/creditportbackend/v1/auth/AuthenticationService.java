package de.swtp13.creditportbackend.v1.auth;

import de.swtp13.creditportbackend.v1.config.JwtService;
import de.swtp13.creditportbackend.v1.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException ae) {
            return null;
        }

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        var jwtToken = jwtService.generateToken(extraClaims, user);
        return new AuthenticationResponse(jwtToken);
    }
}
