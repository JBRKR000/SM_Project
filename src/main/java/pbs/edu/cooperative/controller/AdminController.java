package pbs.edu.cooperative.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.*;
import pbs.edu.cooperative.model.enums.InvoiceCategory;
import pbs.edu.cooperative.responses.MeterReadingRequest;
import pbs.edu.cooperative.responses.WaterCostRequest;
import pbs.edu.cooperative.service.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    TenantService tenantService;
    AccidentService accidentService;
    WaterCostService waterCostService;
    MeterReadingService meterReadingService;
    InvoiceService invoiceService;

    @Autowired
    public AdminController(InvoiceService invoiceService, TenantService tenantService, AccidentService accidentService, WaterCostService waterCostService, MeterReadingService meterReadingService) {
        this.invoiceService = invoiceService;
        this.tenantService = tenantService;
        this.accidentService = accidentService;
        this.waterCostService = waterCostService;
        this.meterReadingService = meterReadingService;
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

    @GetMapping("/allMeterReadings")
    public Page<MeterReading> getAllMeterReadings(Pageable pageable) {
        return meterReadingService.getAllWaterReadings(pageable);
    }

    @PostMapping("/saveMeterReading")
    public ResponseEntity<MeterReading> saveMeterReading(@RequestBody MeterReadingRequest meterReadingRequest) {
        Optional<Tenant> tenant = tenantService.getTenantByNameAndSurname(meterReadingRequest.getTenantName(), meterReadingRequest.getTenantSurname());
        if (tenant.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        MeterReading meterReading = new MeterReading();
        meterReading.setMeterReading((float) meterReadingRequest.getReading());
        meterReading.setReadingDate(meterReadingRequest.getReadingDate());
        meterReading.setTenant(tenant.get());

        MeterReading savedMeterReading = meterReadingService.saveMeterReading(meterReading);
        return ResponseEntity.ok(savedMeterReading);
    }

    @GetMapping("/invoiceCategories")
    public ResponseEntity<Map<String, String>> getAllInvoiceCategories() {
        Map<String, String> categories = Arrays.stream(InvoiceCategory.values())
                .collect(Collectors.toMap(Enum::name, Enum::toString));
        return ResponseEntity.ok(categories);
    }
}
