package pbs.edu.cooperative.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pbs.edu.cooperative.model.Accident;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.service.AccidentService;
import pbs.edu.cooperative.service.TenantService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    TenantService tenantService;
    AccidentService accidentService;

    @Autowired
    public AdminController(TenantService tenantService, AccidentService accidentService){
        this.tenantService = tenantService;
        this.accidentService = accidentService;
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
}
