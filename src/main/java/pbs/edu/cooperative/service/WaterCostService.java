package pbs.edu.cooperative.service;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.WaterCost;

import java.util.Optional;

@Service
public interface WaterCostService {
    Optional<WaterCost> getWaterCost();
    void saveWaterCost(Long costId, float cost);
}
