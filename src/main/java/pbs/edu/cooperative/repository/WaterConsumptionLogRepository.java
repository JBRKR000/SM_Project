package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.WaterConsumptionLog;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaterConsumptionLogRepository extends JpaRepository<WaterConsumptionLog, Integer> {
    List<WaterConsumptionLog> findByTenantTenantId(int tenantId);

    @Query("SELECT w FROM WaterConsumptionLog w WHERE w.tenant.tenantId = :tenantId AND MONTH(w.consumptionDate) = :monthValue ORDER BY w.logId DESC limit 1")
    Optional<WaterConsumptionLog> findFirstByTenantIdAndConsumptionDateMonth(int tenantId, int monthValue);
}