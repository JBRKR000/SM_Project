package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.WaterConsumptionLog;

import java.util.List;

@Repository
public interface WaterConsumptionLogRepository extends JpaRepository<WaterConsumptionLog, Integer> {
    List<WaterConsumptionLog> findByTenantTenantId(int tenantId);
}
