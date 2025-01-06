// MeterReadingRepository.java
package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pbs.edu.cooperative.model.MeterReading;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterReadingRepository extends JpaRepository<MeterReading, Integer> {
    List<MeterReading> findByTenantTenantId(int tenantId);

    @Query("SELECT m FROM MeterReading m WHERE m.tenant.tenantId = :tenantId ORDER BY m.readingDate DESC limit 1")
    Optional<MeterReading> getMeterReadingByTenantId(int tenantId);

    @Query(value = """
    SELECT m.* FROM meter_reading m 
    WHERE m.tenant_id = :tenantId 
      AND m.reading_date >= DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH), '%Y-%m-01')
      AND m.reading_date <= LAST_DAY(DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH))
    ORDER BY m.reading_date DESC, m.reading_id DESC 
    LIMIT 1
""", nativeQuery = true)
    Optional<MeterReading> getLastMonthMeterReadingByTenantId(@Param("tenantId") int tenantId);

    @Query("SELECT m FROM MeterReading m")
    Page<MeterReading> getAllMeterReadings(Pageable pageable);
}