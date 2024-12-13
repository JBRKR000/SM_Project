package pbs.edu.cooperative.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Invoice;
import pbs.edu.cooperative.service.InvoiceService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final InvoiceService invoiceService;

    public UserController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{tenantId}/invoices")
    public Page<Invoice> getAllInvoicesByTenantId(@PathVariable int tenantId, Pageable pageable) {
        return invoiceService.getInvoicesByTenantId(tenantId, pageable);
    }
}
