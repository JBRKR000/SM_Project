package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.responses.ManageTenantsResponse;
import pbs.edu.cooperative.service.FlatService;
import pbs.edu.cooperative.service.TenantService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/tenants")
public class TenantController {

    private final TenantService tenantService;
    private final FlatService flatService;

    @Autowired
    public TenantController(TenantService tenantService, FlatService flatService) {
        this.tenantService = tenantService;
        this.flatService = flatService;
    }

    @GetMapping("/{id}")
    public Optional<Tenant> getTenantById(@PathVariable int id) {
        return tenantService.getTenantById(id);
    }

    @PostMapping
    public Tenant saveTenant(@RequestBody Map<String, Object> tenantData) {
        // Pobierz flatId z JSON
        int flatId = (int) tenantData.get("flatId");
        Optional<Flat> flat = flatService.getFlatById(flatId);

        if (flat.isEmpty()) {
            throw new RuntimeException("Flat not found with id: " + flatId);
        }

        // Tworzenie nowego obiektu Tenant
        Tenant tenant = new Tenant();
        tenant.setPesel((String) tenantData.get("pesel"));
        tenant.setName((String) tenantData.get("name"));
        tenant.setSurname((String) tenantData.get("surname"));
        tenant.setPhoneNumber((String) tenantData.get("phoneNumber"));
        tenant.setIsBacklog((Boolean) tenantData.get("isBacklog"));
        tenant.setTenantsNumber((Integer) tenantData.get("tenantsNumber"));
        tenant.setMail((String) tenantData.get("mail"));
        tenant.setFlat(flat.get());

        return tenantService.saveTenant(tenant);
    }

    @GetMapping
    public Page<ManageTenantsResponse> getAllManageTenantsResponse(Pageable pageable) {
        return tenantService.getAllManageTenants(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteTenantById(@PathVariable int id) {
        tenantService.deleteTenantById(id);
    }

    @GetMapping("/getTenantByFlatId/{flatId}")
    public Tenant getTenantByFlatId(@PathVariable int flatId) {
        return tenantService.getTenantByFlatId(flatId);
    }

}
