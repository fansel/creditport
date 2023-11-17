package de.swtp13.creditportbackend.auth;

import de.swtp13.creditportbackend.config.JwtService;
import de.swtp13.creditportbackend.users.Role;
import de.swtp13.creditportbackend.users.User;
import de.swtp13.creditportbackend.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return AuthenticationResponse.builder()
                    .success(false)
                    .errorMsg("User already exists")
                    .build();
        }
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .success(true)
                .build();
    }

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

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(); //throw specific exception, catch and handle?
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .success(true)
                .token(jwtToken)
                .build();
    }
}
