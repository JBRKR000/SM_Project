package pbs.edu.cooperative.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.WaterCost;
import java.util.Optional;

@Repository
public interface WaterCostRepository extends JpaRepository<WaterCost, Long> {
    @Query("SELECT w FROM WaterCost w ORDER BY w.cost_id DESC")
    Optional<WaterCost> getWaterCost();

    @Modifying
    @Transactional
    @Query("UPDATE WaterCost wc SET wc.cost = :cost WHERE wc.cost_id = :costId")
    void updateCostById(@Param("costId") Long costId, @Param("cost") float cost);

}
