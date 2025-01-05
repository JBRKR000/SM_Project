package pbs.edu.cooperative.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.MeterReading;

import java.util.Optional;

@Service
public interface MeterReadingService {
    MeterReading saveMeterReading(MeterReading meterReading);
    Optional<MeterReading> getMeterReading(int tenantId);
    Optional<MeterReading> getLastMonthMeterReading(int tenantId);
    MeterReading updateMeterReading(MeterReading meterReading);
    void deleteMeterReading(MeterReading meterReading);
}
