package pbs.edu.cooperative.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.User.Role;
import pbs.edu.cooperative.User.User;
import pbs.edu.cooperative.User.UserRepository;
import pbs.edu.cooperative.config.JwtService;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.repository.TenantRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TenantRepository tenantRepository;

    public AuthenticationResponse register(RegisterRequest request) {

        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + request.getTenantId()));

        // Tworzenie nowego użytkownika
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))  // Upewnij się, że hasło jest szyfrowane
                .tenant(tenant) // Przypisanie tenantId z żądania
                .role(Role.USER)
                .build();

        // Zapisanie użytkownika do bazy danych
        userRepository.save(user);

        // Generowanie tokenu JWT
        var jwtToken = jwtService.generateToken(user);

        // Zwracanie tokenu w odpowiedzi
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
    public void logout(String token) {
        String username = jwtService.extractUsername(token); // Wyciągnij nazwę użytkownika z tokenu
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

        if (jwtService.isTokenValid(token, user)) {
            jwtService.revokeToken(token);
        }
    }
    public boolean authenticateToken(String token) {
        try {
            // Wyciągamy nazwę użytkownika z tokenu
            String username = jwtService.extractUsername(token);

            // Szukamy użytkownika po wyciągniętej nazwie
            var user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + username));

            // Sprawdzamy, czy token jest ważny
            return jwtService.isTokenValid(token, user);
        } catch (Exception e) {
            // Jeśli jakikolwiek wyjątek wystąpi (np. token jest niepoprawny), zwrócimy false
            return false;
        }
    }



}
