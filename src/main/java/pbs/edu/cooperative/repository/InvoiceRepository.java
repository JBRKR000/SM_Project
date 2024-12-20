package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pbs.edu.cooperative.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Page<Invoice> findByTenant_TenantId(int tenantId, Pageable pageable);

    @Query("SELECT i FROM Invoice i where i.isPaid = true and i.tenant.tenantId = :tenantId")
    Page<Invoice> findByTenantIdAndIsPaidTrue(Integer tenantId, Pageable pageable);

    @Query("SELECT i FROM Invoice i where i.isPaid = false and i.tenant.tenantId = :tenantId")
    Page<Invoice> findByTenantIdAndIsPaidFalse(Integer tenantId, Pageable pageable);
}
