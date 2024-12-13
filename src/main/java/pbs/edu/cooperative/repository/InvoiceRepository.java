package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pbs.edu.cooperative.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Page<Invoice> findByTenant_TenantId(int tenantId, Pageable pageable);
}
