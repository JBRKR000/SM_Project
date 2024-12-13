package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Invoice;
import pbs.edu.cooperative.service.InvoiceService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{id}")
    public Optional<Invoice> getInvoiceById(@PathVariable int id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping
    public Invoice saveInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
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
