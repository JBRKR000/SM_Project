package pbs.edu.cooperative.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Flat;

import java.util.List;
import java.util.Optional;

@Service
public interface FlatService {
    Optional<Flat> getFlatById(int id);
    Flat saveFlat(Flat flat);
    Page<Flat> getAllFlats(Pageable pageable);
    void deleteFlatById(int id);
    void deleteFlat(Flat flat);
    public List<Integer> getUnoccupiedFlats(int entranceId);
    Page<Flat> getFlatsByStaircaseId(int staircaseId, Pageable pageable);
}
