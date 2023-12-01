package de.swtp13.creditportbackend.v1.auth;

import de.swtp13.creditportbackend.v1.config.JwtService;
import de.swtp13.creditportbackend.v1.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

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
        } catch (AccountStatusException ase) {
            return AuthenticationResponse.builder()
                    .success(false)
                    .errorMsg("Account locked or disabled")
                    .build();
        } catch (BadCredentialsException bce) {
            return AuthenticationResponse.builder()
                    .success(false)
                    .errorMsg("Login Details are incorrect")
                    .build();
        } catch (AuthenticationException ae) {
            return AuthenticationResponse.builder()
                    .success(false)
                    .errorMsg("An Error has occured")
                    .build();
        }

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .success(true)
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }
}
