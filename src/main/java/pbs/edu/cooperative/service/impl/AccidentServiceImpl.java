package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Accident;
import pbs.edu.cooperative.repository.AccidentRepository;
import pbs.edu.cooperative.service.AccidentService;

import java.util.Optional;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    @Autowired
    public AccidentServiceImpl(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    @Override
    public Optional<Accident> getAccident(Integer accidentId) {
        return accidentRepository.findById(accidentId);
    }

    @Override
    public Accident saveAccident(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public Page<Accident> getAccidents(Pageable pageable) {
        return accidentRepository.findAll(pageable);
    }

    @Override
    public void deleteAccidentById(Integer accidentId) {
        accidentRepository.deleteById(accidentId);
    }

    @Override
    public void deleteAccident(Accident accident) {
        accidentRepository.delete(accident);
    }

    @Override
    public Page<Accident> getAccidentsByFlatId(int flatId, Pageable pageable) {
        return accidentRepository.findByFlatFlatId(flatId, pageable);
    }

    @Override
    public Page<Accident> getAllByResolved(Pageable pageable) {
        return accidentRepository.findAllByResolved(pageable);
    }


}
