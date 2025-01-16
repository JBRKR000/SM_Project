package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pbs.edu.cooperative.model.ApartmentStaircase;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.model.MeterReading;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.repository.ApartmentStaircaseRepository;
import pbs.edu.cooperative.responses.MeterReadingRequest;
import pbs.edu.cooperative.service.FlatService;
import pbs.edu.cooperative.service.TenantService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/flats")
public class FlatController {

    private final FlatService flatService;
    TenantService tenantService;
    ApartmentStaircaseRepository apartmentStaircaseRepository;

    @Autowired
    public FlatController(FlatService flatService, TenantService tenantService, ApartmentStaircaseRepository apartmentStaircaseRepository) {
        this.flatService = flatService;
        this.tenantService = tenantService;
        this.apartmentStaircaseRepository = apartmentStaircaseRepository;
    }

    @GetMapping("/{id}")
    public Optional<Flat> getFlatById(@PathVariable int id) {
        return flatService.getFlatById(id);
    }

    @PostMapping
    public Flat saveFlat(@RequestBody Flat flat) {
        // Pobierz istniejącą klatkę schodową
        ApartmentStaircase staircase = apartmentStaircaseRepository.findById(flat.getApartmentStaircase().getApartmentStaircaseId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono klatki schodowej o ID: " + flat.getApartmentStaircase().getApartmentStaircaseId()));

        // Ustaw zarządzany obiekt klatki w mieszkaniu
        flat.setApartmentStaircase(staircase);

        // Zapisz mieszkanie
        return flatService.saveFlat(flat);
    }

    @PutMapping("/{id}")
    public Flat updateFlat(@PathVariable int id, @RequestBody Map<String, Object> flatData) {
        // Pobierz istniejące mieszkanie
        Optional<Flat> existingFlatOpt = flatService.getFlatById(id);

        if (existingFlatOpt.isEmpty()) {
            throw new RuntimeException("Flat not found with id: " + id);
        }

        Flat flat = existingFlatOpt.get();

        // Aktualizuj tylko wybrane pola
        if (flatData.containsKey("flatNumber")) {
            flat.setFlatNumber((Integer) flatData.get("flatNumber"));
        }
        if (flatData.containsKey("surface")) {
            Object surfaceValue = flatData.get("surface");
            if (surfaceValue instanceof Number) {
                flat.setSurface(((Number) surfaceValue).floatValue());
            } else {
                throw new IllegalArgumentException("Surface value is not a valid number.");
            }
        }

        // Zapisz zmiany
        return flatService.saveFlat(flat);
    }





    @GetMapping
    public Page<Flat> getAllFlats(Pageable pageable) {
        return flatService.getAllFlats(pageable);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteFlatById(@PathVariable int id) {
        flatService.deleteFlatById(id);
    }

    @GetMapping("/getByStaircaseId/{id}")
    public Page<Flat> getFlatsByStaircaseId(@PathVariable int id, Pageable pageable) {
        return flatService.getFlatsByStaircaseId(id, pageable);
    }
    @GetMapping("/getByTenantId/{tenantId}")
    public Flat getFlatByTenantId(@PathVariable int tenantId) {
        return flatService.getFlatByTenantId(tenantId);
    }

    @GetMapping("/getByTenantNameAndSurname/{name}/{surname}")
    public Flat getFlatByTenantNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return flatService.getByTenantNameAndSurname(name, surname);
    }



}
