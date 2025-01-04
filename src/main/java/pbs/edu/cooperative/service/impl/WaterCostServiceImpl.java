package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.WaterCost;
import pbs.edu.cooperative.repository.WaterCostRepository;
import pbs.edu.cooperative.service.WaterCostService;

import java.util.Optional;

@Service
public class WaterCostServiceImpl implements WaterCostService {

    private final WaterCostRepository waterCostRepository;

    @Autowired
    public WaterCostServiceImpl(WaterCostRepository waterCostRepository) {
        this.waterCostRepository = waterCostRepository;
    }

    @Override
    public Optional<WaterCost> getWaterCost() {
        return waterCostRepository.getWaterCost();
    }

    @Override
    public void saveWaterCost(Long costId, float cost) {
        waterCostRepository.updateCostById(costId, cost);
    }
}
