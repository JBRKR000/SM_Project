package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.ApartmentStaircase;
import pbs.edu.cooperative.repository.ApartmentStaircaseRepository;
import pbs.edu.cooperative.service.ApartmentStaircaseService;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentStaircaseServiceImpl implements ApartmentStaircaseService {

    private final ApartmentStaircaseRepository apartmentStaircaseRepository;

    @Autowired
    public ApartmentStaircaseServiceImpl(ApartmentStaircaseRepository apartmentStaircaseRepository) {
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
}
