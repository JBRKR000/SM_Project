package pbs.edu.cooperative.auth;


import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.User.Role;
import pbs.edu.cooperative.User.User;
import pbs.edu.cooperative.User.UserRepository;
import pbs.edu.cooperative.config.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))  // Upewnij się, że hasło jest szyfrowane
                .role(Role.USER)
                .build();
        userRepository.save(user);  // Sprawdź, czy użytkownik jest poprawnie zapisywany w bazie
        var jwtToken = jwtService.generateToken(user);  // Generowanie tokenu
        return AuthenticationResponse.builder()
                .token(jwtToken)  // Zwrócenie tokenu
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
