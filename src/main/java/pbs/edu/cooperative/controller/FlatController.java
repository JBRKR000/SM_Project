package pbs.edu.cooperative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.service.FlatService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/flats")
public class FlatController {

    private final FlatService flatService;

    @Autowired
    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    @GetMapping("/{id}")
    public Optional<Flat> getFlatById(@PathVariable int id) {
        return flatService.getFlatById(id);
    }

    @PostMapping
    public Flat saveFlat(@RequestBody Flat flat) {
        return flatService.saveFlat(flat);
    }

    @GetMapping
    public Page<Flat> getAllFlats(Pageable pageable) {
        return flatService.getAllFlats(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteFlatById(@PathVariable int id) {
        flatService.deleteFlatById(id);
    }

    @GetMapping("/getByStaircaseId/{id}")
    public Page<Flat> getFlatsByStaircaseId(@PathVariable int id, Pageable pageable) {
        return flatService.getFlatsByStaircaseId(id, pageable);
    }

}
