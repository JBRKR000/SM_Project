package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.service.TenantService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/tenants")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/{id}")
    public Optional<Tenant> getTenantById(@PathVariable int id) {
        return tenantService.getTenantById(id);
    }

    @PostMapping
    public Tenant saveTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    @GetMapping
    public Page<Tenant> getAllTenants(Pageable pageable) {
        return tenantService.getAllTenants(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteTenantById(@PathVariable int id) {
        tenantService.deleteTenantById(id);
    }
}
