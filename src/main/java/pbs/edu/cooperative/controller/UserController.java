package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Invoice;
import pbs.edu.cooperative.service.InvoiceService;
import pbs.edu.cooperative.config.JwtService;  // Dodaj import dla JwtService

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final InvoiceService invoiceService;
    private final JwtService jwtService;  // Dodaj pole dla JwtService

    @Autowired
    public UserController(InvoiceService invoiceService, JwtService jwtService) {  // Wstrzykujemy JwtService
        this.invoiceService = invoiceService;
        this.jwtService = jwtService;
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
}
