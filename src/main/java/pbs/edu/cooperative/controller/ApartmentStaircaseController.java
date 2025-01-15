package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.ApartmentStaircase;
import pbs.edu.cooperative.model.Block;
import pbs.edu.cooperative.repository.BlockRepository;
import pbs.edu.cooperative.service.ApartmentStaircaseService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/apartment_staircases")
public class ApartmentStaircaseController {

    private final ApartmentStaircaseService apartmentStaircaseService;
    private final BlockRepository blockRepository;

    @Autowired
    public ApartmentStaircaseController(ApartmentStaircaseService apartmentStaircaseService, BlockRepository blockRepository) {
        this.apartmentStaircaseService = apartmentStaircaseService;
        this.blockRepository = blockRepository;
    }

    @GetMapping("/{id}")
    public Optional<ApartmentStaircase> getApartmentStaircaseById(@PathVariable int id) {
        return apartmentStaircaseService.getApartmentStaircaseById(id);
    }

    @PostMapping
    public ApartmentStaircase saveApartmentStaircase(@RequestBody ApartmentStaircase apartmentStaircase) {
        // Pobierz zarządzany obiekt Block
        Block block = blockRepository.findById(apartmentStaircase.getBlock().getBlockId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono bloku o ID: " + apartmentStaircase.getBlock().getBlockId()));

        // Ustaw zarządzany obiekt Block w klatce
        apartmentStaircase.setBlock(block);

        // Zapisz klatkę
        return apartmentStaircaseService.saveApartmentStaircase(apartmentStaircase);
    }

    @PutMapping("/{id}")
    public ApartmentStaircase updateApartmentStaircase(@PathVariable int id, @RequestBody Map<String, Object> staircaseData) {
        // Pobierz istniejącą klatkę schodową
        Optional<ApartmentStaircase> existingStaircaseOpt = apartmentStaircaseService.getApartmentStaircaseById(id);

        if (existingStaircaseOpt.isEmpty()) {
            throw new RuntimeException("Apartment Staircase not found with id: " + id);
        }

        ApartmentStaircase staircase = existingStaircaseOpt.get();

        // Aktualizuj tylko wybrane pola
        if (staircaseData.containsKey("sharedSurface")) {
            Object sharedSurfaceValue = staircaseData.get("sharedSurface");
            if (sharedSurfaceValue instanceof Number) {
                staircase.setSharedSurface(((Number) sharedSurfaceValue).floatValue());
            } else {
                throw new IllegalArgumentException("Shared Surface value is not a valid number.");
            }
        }

        if (staircaseData.containsKey("staircaseNumber")) {
            staircase.setStaircaseNumber((Integer) staircaseData.get("staircaseNumber"));
        }

        // Zapisz zmiany
        return apartmentStaircaseService.saveApartmentStaircase(staircase);
    }



    @GetMapping
    public Page<ApartmentStaircase> getAllApartmentStaircases(Pageable pageable) {
        return apartmentStaircaseService.getAllApartmentStaircases(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteApartmentStaircaseById(@PathVariable int id) {
        apartmentStaircaseService.deleteApartmentStaircaseById(id);
    }
    
    @GetMapping("/block/{block_id}")
    public Page<ApartmentStaircase> getApartmentStaircasesByBlockId(@PathVariable int block_id, Pageable pageable) {
        return apartmentStaircaseService.findStaircasesByBlockId(block_id, pageable);
    }

    @GetMapping("/{staircaseId}/electricity-cost")
    public Map<Integer, Float> getElectricityCostDistribution(@PathVariable int staircaseId, @RequestParam float totalElectricityCost) {
        return apartmentStaircaseService.calculateElectricityCost(staircaseId, totalElectricityCost);
    }
}
