package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.ApartmentStaircase;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.repository.ApartmentStaircaseRepository;
import pbs.edu.cooperative.repository.FlatRepository;
import pbs.edu.cooperative.service.ApartmentStaircaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApartmentStaircaseServiceImpl implements ApartmentStaircaseService {

    private final FlatRepository flatRepository;
    private final ApartmentStaircaseRepository apartmentStaircaseRepository;

    @Autowired
    public ApartmentStaircaseServiceImpl(FlatRepository flatRepository, ApartmentStaircaseRepository apartmentStaircaseRepository) {
        this.flatRepository = flatRepository;
        this.apartmentStaircaseRepository = apartmentStaircaseRepository;
    }

    @Override
    public Optional<ApartmentStaircase> getApartmentStaircaseById(int id) {
        return apartmentStaircaseRepository.findById(id);
    }

    @Override
    public ApartmentStaircase saveApartmentStaircase(ApartmentStaircase apartmentStaircase) {
        return apartmentStaircaseRepository.save(apartmentStaircase);
    }

    @Override
    public Page<ApartmentStaircase> getAllApartmentStaircases(Pageable pageable) {
        return apartmentStaircaseRepository.findAll(pageable);
    }

    @Override
    public void deleteApartmentStaircaseById(int id) {
        apartmentStaircaseRepository.deleteById(id);
    }

    @Override
    public void deleteApartmentStaircase(ApartmentStaircase apartmentStaircase) {
        apartmentStaircaseRepository.delete(apartmentStaircase);
    }

    @Override
    public Page<ApartmentStaircase> findStaircasesByBlockId(int blockId, Pageable pageable) {
        return apartmentStaircaseRepository.findByBlockId(blockId, pageable);
    }

    public Map<Integer, Float> calculateElectricityCost(int staircaseId, float totalElectricityCost) {
        List<Flat> flats = flatRepository.getFlatsByApartmentStaircaseId(staircaseId, Pageable.unpaged()).getContent();
        float totalArea = (float) flats.stream().mapToDouble(Flat::getSurface).sum();

        Map<Integer, Float> costDistribution = new HashMap<>();
        for (Flat flat : flats) {
            float cost = (flat.getSurface() / totalArea) * totalElectricityCost;
            costDistribution.put(flat.getFlatId(), cost);
        }
        return costDistribution;
    }
}
