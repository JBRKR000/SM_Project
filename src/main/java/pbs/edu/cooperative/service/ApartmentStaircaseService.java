package pbs.edu.cooperative.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.ApartmentStaircase;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface ApartmentStaircaseService {
    Optional<ApartmentStaircase> getApartmentStaircaseById(int id);
    ApartmentStaircase saveApartmentStaircase(ApartmentStaircase apartmentStaircase);
    Page<ApartmentStaircase> getAllApartmentStaircases(Pageable pageable);
    void deleteApartmentStaircaseById(int id);
    void deleteApartmentStaircase(ApartmentStaircase apartmentStaircase);
    Page<ApartmentStaircase> findStaircasesByBlockId(int blockId, Pageable pageable);
    Map<Integer, Float> calculateElectricityCost(int staircaseId, float totalElectricityCost);
}
