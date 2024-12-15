package pbs.edu.cooperative.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Tenant;

import java.util.Optional;

@Service
public interface TenantService {
    Optional<Tenant> getTenantById(int id);
    Tenant saveTenant(Tenant tenant);
    Page<Tenant> getAllTenants(Pageable pageable);
    void deleteTenantById(int id);
    void deleteTenant(Tenant tenant);
    Page<Tenant> getAllBacklogTenants(Pageable pageable);
}
