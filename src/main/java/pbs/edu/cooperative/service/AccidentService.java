package pbs.edu.cooperative.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Accident;

import java.util.Optional;
@Service
public interface AccidentService {
    Optional<Accident> getAccident(Integer accidentId);
    Accident setAccident(Accident accident);
    Page<Accident> getAccidents(Pageable pageable);
    void deleteAccidentById(Integer accidentId);
    void deleteAccident(Accident accident);
}
