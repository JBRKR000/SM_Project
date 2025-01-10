package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.model.WaterConsumptionLog;
import pbs.edu.cooperative.responses.ManageTenantsResponse;
import pbs.edu.cooperative.service.FlatService;
import pbs.edu.cooperative.service.TenantService;
import pbs.edu.cooperative.service.WaterConsumptionLogService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/tenants")
public class TenantController {

    private final TenantService tenantService;
    private final FlatService flatService;
    private final WaterConsumptionLogService waterConsumptionLogService;

    @Autowired
    public TenantController(TenantService tenantService, FlatService flatService, WaterConsumptionLogService waterConsumptionLogService) {
        this.tenantService = tenantService;
        this.flatService = flatService;
        this.waterConsumptionLogService = waterConsumptionLogService;
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

    @PutMapping("/{tenantId}")
    public Tenant updateTenant(@PathVariable int tenantId, @RequestBody Map<String, Object> tenantData) {
        // Pobierz istniejącego tenant
        Optional<Tenant> existingTenantOpt = tenantService.getTenantById(tenantId);

        if (existingTenantOpt.isEmpty()) {
            throw new RuntimeException("Tenant not found with id: " + tenantId);
        }

        Tenant tenant = existingTenantOpt.get();

        // Aktualizuj tylko wybrane pola
        tenant.setPesel((String) tenantData.get("pesel"));
        tenant.setName((String) tenantData.get("name"));
        tenant.setSurname((String) tenantData.get("surname"));
        tenant.setPhoneNumber((String) tenantData.get("phoneNumber"));
        tenant.setMail((String) tenantData.get("mail"));
        tenant.setTenantsNumber((Integer) tenantData.get("tenantsNumber"));

        // Zapisz zmiany
        return tenantService.saveTenant(tenant);
    }


    @GetMapping
    public Page<ManageTenantsResponse> getAllManageTenantsResponse(Pageable pageable) {
        return tenantService.getAllManageTenants(pageable);
    }



    @GetMapping("/getTenantByFlatId/{flatId}")
    public Tenant getTenantByFlatId(@PathVariable int flatId) {
        return tenantService.getTenantByFlatId(flatId);
    }

    @GetMapping("/getTenantByIsBacklog/{isBacklog}")
    public Page<Tenant> getTenantByIsBacklog(@PathVariable boolean isBacklog, Pageable pageable) {
        return tenantService.getTenantByIsBacklog(isBacklog, pageable);
    }
    @GetMapping("/getAllTenants")
    public Page<Tenant> getAllTenants(Pageable pageable) {
        return tenantService.getAllTenants(pageable);
    }

    @PatchMapping("/{id}/updateBacklog")
    public ResponseEntity<Tenant> updateTenantBacklog(@PathVariable int id, @RequestBody Map<String, Boolean> updateData) {
        Optional<Tenant> tenantOptional = tenantService.getTenantById(id);
        if (tenantOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tenant tenant = tenantOptional.get();
        tenant.setIsBacklog(updateData.get("isBacklog"));
        Tenant updatedTenant = tenantService.saveTenant(tenant);
        return ResponseEntity.ok(updatedTenant);
    }
    @GetMapping("/getTenantByNameAndSurname/{name}/{surname}")
    public Optional<Tenant> getTenantByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return tenantService.getTenantByNameAndSurname(name, surname);
    }
}
