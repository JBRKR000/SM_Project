package pbs.edu.cooperative.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Invoice;

import java.util.Optional;

@Service
public interface InvoiceService {

    Optional<Invoice> getInvoiceById(int id);
    Invoice saveInvoice(Invoice invoice);
    Page<Invoice> getAllInvoices(Pageable pageable);
    void deleteInvoiceById(int id);
    void deleteInvoice(Invoice invoice);
    Page<Invoice> getInvoicesByTenantId(int tenantId, Pageable pageable);  // Zmieniono z List<Invoice> na Page<Invoice>
    Page<Invoice> getPaidInvoicesByTenantId(Integer tenantId, Pageable pageable);
    Page<Invoice> getUnpaidInvoicesByTenantId(Integer tenantId, Pageable pageable);

}
