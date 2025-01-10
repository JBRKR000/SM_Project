package pbs.edu.cooperative.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pbs.edu.cooperative.model.Accident;

import pbs.edu.cooperative.service.AccidentService;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/accident")
public class AccidentController {
    AccidentService accidentService;

    public AccidentController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }
    @GetMapping("/{id}")
    public Optional<Accident> getAccidentById(@PathVariable("id") int id) {
        return accidentService.getAccident(id);
    }

    @PostMapping
    public Accident saveAccident(@RequestBody Accident accident) {
        return accidentService.saveAccident(accident);
    }

    @GetMapping
    public Page<Accident> getAllAcccidents(Pageable pageable) {
        return accidentService.getAllAccidents(pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteAccidentById(@PathVariable int id) {
        accidentService.deleteAccidentById(id);
    }


}
