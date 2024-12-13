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
    public static void getToken(){

    }
}
