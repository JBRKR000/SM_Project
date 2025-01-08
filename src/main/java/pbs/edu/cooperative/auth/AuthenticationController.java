package pbs.edu.cooperative.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pbs.edu.cooperative.responses.TokenRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok("User logged out successfully.");
    }

    // Metoda kontrolera
    @PostMapping("/authenticatetoken")
    public ResponseEntity<String> authenticateToken(@RequestBody TokenRequest request) {
        String token = request.getToken(); // Odbieramy token z obiektu

        boolean isValid = authenticationService.authenticateToken(token);
        if (isValid) {
            return ResponseEntity.ok("Token is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }
    }
}
