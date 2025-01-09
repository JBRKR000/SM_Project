package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Invoice;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.model.enums.InvoiceCategory;
import pbs.edu.cooperative.service.InvoiceService;
import pbs.edu.cooperative.service.TenantService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final TenantService tenantService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, TenantService tenantService) {
        this.invoiceService = invoiceService;
        this.tenantService = tenantService;
    }

    @GetMapping("/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable int id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<Invoice> saveInvoice(@RequestBody Map<String, Object> invoiceData) {
        int tenantId = (int) invoiceData.get("tenantId");
        Optional<Tenant> tenantOptional = tenantService.getTenantById(tenantId);
        if (tenantOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Tenant tenant = tenantOptional.get();
        Invoice invoice = new Invoice();
        invoice.setInvoiceId((int) invoiceData.get("invoiceId"));
        invoice.setInvoiceNumber((String) invoiceData.get("invoiceNumber"));
        invoice.setTenant(tenant);
        invoice.setInvoiceCategory(InvoiceCategory.valueOf((String) invoiceData.get("invoiceCategory")));
        invoice.setIssueDate(LocalDate.parse((String) invoiceData.get("issueDate")));
        invoice.setPaymentDate(LocalDate.parse((String) invoiceData.get("paymentDate")));
        invoice.setSum(Float.parseFloat((String) invoiceData.get("sum")));
        invoice.setPaid((boolean) invoiceData.get("isPaid"));

        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(savedInvoice);
    }

    @GetMapping
    public Page<Invoice> getAllInvoices(Pageable pageable) {
        return invoiceService.getAllInvoices(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoiceById(@PathVariable int id) {
        invoiceService.deleteInvoiceById(id);
    }

}
