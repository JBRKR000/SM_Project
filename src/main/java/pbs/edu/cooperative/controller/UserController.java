package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import pbs.edu.cooperative.User.User;
import pbs.edu.cooperative.User.UserRepository;
import pbs.edu.cooperative.auth.ChangePasswordRequest;
import pbs.edu.cooperative.model.*;

import pbs.edu.cooperative.service.*;
import pbs.edu.cooperative.config.JwtService;  // Dodaj import dla JwtService
import pbs.edu.cooperative.service.impl.WaterConsumptionLogServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final InvoiceService invoiceService;
    private final TenantService tenantService;
    private final AccidentService accidentService;
    private final FlatService flatService;
    private final JwtService jwtService;  // Dodaj pole dla JwtService
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WaterConsumptionLogService waterConsumptionLogService;
    private final WaterCostService waterCostService;
    private final MeterReadingService meterReadingService;

    @Autowired
    public UserController(InvoiceService invoiceService, WaterConsumptionLogService waterConsumptionLogService, TenantService tenantService, AccidentService accidentService, FlatService flatService, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, WaterCostService waterCostService, MeterReadingService meterReadingService) {
        this.invoiceService = invoiceService;
        this.tenantService = tenantService;
        this.accidentService = accidentService;
        this.flatService = flatService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.waterConsumptionLogService = waterConsumptionLogService;
        this.waterCostService = waterCostService;
        this.meterReadingService = meterReadingService;
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/invoices")
    public Page<Invoice> getInvoicesByTenantId(Pageable pageable, @RequestHeader("Authorization") String authHeader) {
        // Zakładam, że tenantId jest w tokenie JWT
        String token = authHeader.substring(7);  // Usuń prefiks "Bearer " z tokenu
        int tenantId = jwtService.extractTenantIdFromToken(token);  // Korzystamy z metody do ekstrakcji tenantId z tokenu
        return invoiceService.getInvoicesByTenantId(tenantId, pageable);
    }

    @GetMapping("/paidinvoices")
    public Page<Invoice> getPaidInvoicesByTenantId(Pageable pageable, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);  // Usuń prefiks "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token);
        return invoiceService.getPaidInvoicesByTenantId(tenantId, pageable);
    }
    @GetMapping("/unpaidinvoices")
    public Page<Invoice> getUnpaidInvoicesByTenantId(Pageable pageable, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);  // Usuń prefiks "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token);
        return invoiceService.getUnpaidInvoicesByTenantId(tenantId, pageable);
    }

    @PostMapping("/accidents")
    public Accident createAccident(@RequestBody Accident accidentRequest, @RequestHeader("Authorization") String authHeader) {
        // Wyciągnij token i tenantId
        String token = authHeader.substring(7); // Usuń "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token);

        // Pobierz obiekt Tenant na podstawie tenantId
        Tenant tenant = tenantService.getTenantById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Przypisz Flat do Accident na podstawie Tenant
        accidentRequest.setFlat(tenant.getFlat());
        accidentRequest.setAccidentDate(LocalDateTime.now()); // Ustaw aktualną datę
        accidentRequest.setResolved(false); // Domyślnie oznacz jako nierozwiązane

        // Zapisz i zwróć Accident
        return accidentService.saveAccident(accidentRequest);
    }
    @GetMapping("/accidents")
    public Page<Accident> getAccidentsByTenant(Pageable pageable, @RequestHeader("Authorization") String authHeader) {
        // Wyciągnij token i tenantId
        String token = authHeader.substring(7); // Usuń "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token); // Wyciągnij tenantId z tokenu

        // Pobierz obiekt Tenant na podstawie tenantId
        Tenant tenant = tenantService.getTenantById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Pobierz wypadki powiązane z mieszkaniem Tenant
        return accidentService.getAccidentsByFlatId(tenant.getFlat().getFlatId(), pageable);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String encodedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/send-consumption")
    public WaterConsumptionLog declareWaterConsumption(
            @RequestBody WaterConsumptionLog logRequest,
            @RequestHeader("Authorization") String authHeader) {

        // Wyciągnij token i tenantId
        String token = authHeader.substring(7); // Usuń "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token);

        // Pobierz Tenant
        Tenant tenant = tenantService.getTenantById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Pobierz meter_reading_id powiązane z tenantId
        MeterReading meterReading = meterReadingService.getMeterReading(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Meter Reading not found for Tenant ID: " + tenantId));

        // Przypisz Tenant i MeterReading do logu oraz ustaw datę
        logRequest.setTenant(tenant);
        logRequest.setMeterReading(meterReading);
        logRequest.setConsumptionDate(LocalDate.now());

        // Zapisz log
        return waterConsumptionLogService.saveLog(logRequest);
    }


    @GetMapping("/role")
    public ResponseEntity<Map<String, String>> getUserRole(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // Usunięcie prefiksu "Bearer "
        String role = jwtService.extractRoleFromToken(token); // Wyciągnięcie roli z tokena

        // Zwrócenie JSON-a w formacie {"role": "USER"}
        return ResponseEntity.ok(Map.of("role", role));
    }
    @GetMapping("/getByTenantId/{tenantId}")
    public Integer getFlatByTenantId(@PathVariable int tenantId) {
        return flatService.getFlatIdByTenantId(tenantId);
    }

    @PostMapping("/saveAccident")
    public Accident saveAccident(@RequestBody Map<Object,String> accidentData) {
        int flatId = Integer.parseInt(accidentData.get("flat_id"));
        Optional<Flat> flat = flatService.getFlatById(flatId);
        if (flat.isEmpty()) {
            throw new IllegalArgumentException("Flat not found with ID: " + flatId);
        }
        Accident accident = new Accident();
        accident.setAccidentDate(LocalDateTime.now());
        accident.setResolved(false);
        accident.setDescription(accidentData.get("description"));
        accident.setFlat(flat.get());
        return accidentService.saveAccident(accident);
    }
    @GetMapping("/waterCost")
    public Optional<WaterCost> getWaterCost() {
        return waterCostService.getWaterCost();
    }

    @GetMapping("/getLatestMeterReading")
    public ResponseEntity<MeterReading> getLatestMeterReading(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        int tenantId = jwtService.extractTenantIdFromToken(token);
        Optional<MeterReading> meterReading = meterReadingService.getMeterReading(tenantId);
        if (meterReading.isPresent()) {
            return ResponseEntity.ok(meterReading.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/getLastMonthMeterReading")
    public ResponseEntity<MeterReading> getLastMonthMeterReading(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        int tenantId = jwtService.extractTenantIdFromToken(token);
        Optional<MeterReading> lastMonthMeterReading = meterReadingService.getLastMonthMeterReading(tenantId);
        if (lastMonthMeterReading.isPresent()) {
            return ResponseEntity.ok(lastMonthMeterReading.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

