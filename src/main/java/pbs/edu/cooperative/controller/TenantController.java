package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.service.TenantService;

import java.util.Optional;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/user/{id}")
    public Optional<Tenant> getTenantById(@PathVariable int id) {
        return tenantService.getTenantById(id);
    }

    @PostMapping
    public Tenant saveTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }

    @GetMapping("/user")
    public Page<Tenant> getAllTenants(Pageable pageable) {
        return tenantService.getAllTenants(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteTenantById(@PathVariable int id) {
        tenantService.deleteTenantById(id);
    }
}
