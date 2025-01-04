package pbs.edu.cooperative.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Accident;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.model.WaterCost;
import pbs.edu.cooperative.responses.WaterCostRequest;
import pbs.edu.cooperative.service.AccidentService;
import pbs.edu.cooperative.service.TenantService;
import pbs.edu.cooperative.service.WaterCostService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    TenantService tenantService;
    AccidentService accidentService;
    WaterCostService waterCostService;

    @Autowired
    public AdminController(TenantService tenantService, AccidentService accidentService, WaterCostService waterCostService) {
        this.tenantService = tenantService;
        this.accidentService = accidentService;
        this.waterCostService = waterCostService;
    }

    @GetMapping("/all-data")
    public ResponseEntity<String> getAllData() {
        return ResponseEntity.ok("This is admin-only data.");
    }

    @GetMapping("/isBacklog")
    public Page<Tenant> getAllBacklogTenants(Pageable pageable) {
        return tenantService.getAllBacklogTenants(pageable);
    }

    @GetMapping("/isResolved")
    public Page<Accident> getAllUnresolvedAccidents(Pageable pageable) {
        return accidentService.getAllByResolved(pageable);
    }
    @PostMapping("/saveWaterCost")
    public void saveWaterCost(@RequestBody WaterCostRequest request) {
        waterCostService.saveWaterCost(request.getCostId(), request.getCost());
    }
    @GetMapping("/waterCost")
    public Optional<WaterCost> getWaterCost() {
        return waterCostService.getWaterCost();
    }
}
