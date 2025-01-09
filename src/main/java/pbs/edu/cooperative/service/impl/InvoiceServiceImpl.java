package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Invoice;
import pbs.edu.cooperative.model.enums.InvoiceCategory;
import pbs.edu.cooperative.repository.InvoiceRepository;
import pbs.edu.cooperative.service.InvoiceService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Optional<Invoice> getInvoiceById(int id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Page<Invoice> getAllInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public void deleteInvoiceById(int id) {
        invoiceRepository.deleteById(id);
    }

    @Override
    public void deleteInvoice(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    @Override
    public Page<Invoice> getInvoicesByTenantId(int tenantId ,Pageable pageable) {
        return invoiceRepository.findByTenant_TenantId(tenantId, pageable);
    }

    @Override
    public Page<Invoice> getPaidInvoicesByTenantId(Integer tenantId, Pageable pageable) {
        return invoiceRepository.findByTenantIdAndIsPaidTrue(tenantId, pageable);
    }
    @Override
    public Page<Invoice> getUnpaidInvoicesByTenantId(Integer tenantId, Pageable pageable) {
        return invoiceRepository.findByTenantIdAndIsPaidFalse(tenantId, pageable);
    }

    @Override
    public List<InvoiceCategory> getAllInvoiceCategories() {
        return Arrays.asList(InvoiceCategory.values());
    }
}
