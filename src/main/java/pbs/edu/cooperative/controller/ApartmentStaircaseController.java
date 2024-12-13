package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.ApartmentStaircase;
import pbs.edu.cooperative.service.ApartmentStaircaseService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/apartment_staircases")
public class ApartmentStaircaseController {

    private final ApartmentStaircaseService apartmentStaircaseService;

    @Autowired
    public ApartmentStaircaseController(ApartmentStaircaseService apartmentStaircaseService) {
        this.apartmentStaircaseService = apartmentStaircaseService;
    }

    @GetMapping("/{id}")
    public Optional<ApartmentStaircase> getApartmentStaircaseById(@PathVariable int id) {
        return apartmentStaircaseService.getApartmentStaircaseById(id);
    }

    @PostMapping
    public ApartmentStaircase saveApartmentStaircase(@RequestBody ApartmentStaircase apartmentStaircase) {
        return apartmentStaircaseService.saveApartmentStaircase(apartmentStaircase);
    }

    @GetMapping
    public Page<ApartmentStaircase> getAllApartmentStaircases(Pageable pageable) {
        return apartmentStaircaseService.getAllApartmentStaircases(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteApartmentStaircaseById(@PathVariable int id) {
        apartmentStaircaseService.deleteApartmentStaircaseById(id);
    }
}
