package pbs.edu.cooperative.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/all-data")
    public ResponseEntity<String> getAllData() {
        return ResponseEntity.ok("This is admin-only data.");
    }
}
