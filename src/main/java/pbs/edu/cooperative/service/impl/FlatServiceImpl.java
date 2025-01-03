package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.repository.FlatRepository;
import pbs.edu.cooperative.service.FlatService;

import java.util.List;
import java.util.Optional;

@Service
public class FlatServiceImpl implements FlatService {

    private final FlatRepository flatRepository;

    @Autowired
    public FlatServiceImpl(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    @Override
    public Optional<Flat> getFlatById(int id) {
        return flatRepository.findById(id);
    }

    @Override
    public Flat saveFlat(Flat flat) {
        return flatRepository.save(flat);
    }

    @Override
    public Page<Flat> getAllFlats(Pageable pageable) {
        return flatRepository.findAll(pageable);
    }

    @Override
    public void deleteFlatById(int id) {
        flatRepository.deleteById(id);
    }

    @Override
    public void deleteFlat(Flat flat) {
        flatRepository.delete(flat);
    }

    @Override
    public List<Integer> getUnoccupiedFlats(int entranceId) {
        return flatRepository.findUnoccupiedFlatsByEntranceId(entranceId);
    }

    @Override
    public Page<Flat> getFlatsByStaircaseId(int staircaseId, Pageable pageable) {
        return flatRepository.getFlatsByApartmentStaircaseId(staircaseId, pageable);
    }

    @Override
    public Integer getFlatIdByTenantId(int tenantId) {
        return flatRepository.getFlatIdByTenantId(tenantId);
    }
    @Override
    public Flat getFlatByTenantId(int tenantId) {
        return flatRepository.getFlatByTenantId(tenantId);
    }

}
