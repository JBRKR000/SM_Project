package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import pbs.edu.cooperative.model.Accident;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.model.Invoice;

import pbs.edu.cooperative.model.WaterConsumptionLog;
import pbs.edu.cooperative.service.AccidentService;
import pbs.edu.cooperative.service.InvoiceService;
import pbs.edu.cooperative.config.JwtService;  // Dodaj import dla JwtService
import pbs.edu.cooperative.service.TenantService;
import pbs.edu.cooperative.service.WaterConsumptionLogService;
import pbs.edu.cooperative.service.impl.WaterConsumptionLogServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final InvoiceService invoiceService;
    private final TenantService tenantService;
    private final AccidentService accidentService;
    private final WaterConsumptionLogService waterConsumptionLogService;
    private final JwtService jwtService;  // Dodaj pole dla JwtService

    @Autowired
    public UserController(InvoiceService invoiceService,WaterConsumptionLogService waterConsumptionLogService, TenantService tenantService, AccidentService accidentService, JwtService jwtService, WaterConsumptionLogServiceImpl waterConsumptionLogServiceImpl) {  // Wstrzykujemy JwtService
        this.invoiceService = invoiceService;
        this.tenantService = tenantService;
        this.accidentService = accidentService;
        this.jwtService = jwtService;
        this.waterConsumptionLogService = waterConsumptionLogService;
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

    @PostMapping("/water-consumption")
    public WaterConsumptionLog declareWaterConsumption(@RequestBody WaterConsumptionLog logRequest, @RequestHeader("Authorization") String authHeader) {
        // Wyciągnij token i tenantId
        String token = authHeader.substring(7); // Usuń "Bearer "
        int tenantId = jwtService.extractTenantIdFromToken(token);

        // Pobierz Tenant
        Tenant tenant = tenantService.getTenantById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found with ID: " + tenantId));

        // Przypisz Tenant do logu i ustaw datę
        logRequest.setTenant(tenant);
        logRequest.setConsumptionDate(LocalDate.now());

        // Zapisz log
        return waterConsumptionLogService.saveLog(logRequest);
    }

}
