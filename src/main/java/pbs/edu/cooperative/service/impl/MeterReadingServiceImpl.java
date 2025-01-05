package pbs.edu.cooperative.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.MeterReading;
import pbs.edu.cooperative.repository.MeterReadingRepository;
import pbs.edu.cooperative.service.MeterReadingService;

import java.util.Optional;

@Service
public class MeterReadingServiceImpl implements MeterReadingService {

    private final MeterReadingRepository meterReadingRepository;

    public MeterReadingServiceImpl(MeterReadingRepository meterReadingRepository) {
        this.meterReadingRepository = meterReadingRepository;
    }

    @Override
    public MeterReading saveMeterReading(MeterReading meterReading) {
        return meterReadingRepository.save(meterReading);
    }

    @Override
    public Optional<MeterReading> getMeterReading(int tenantId) {
        return meterReadingRepository.getMeterReadingByTenantId(tenantId);
    }

    @Override
    public Optional<MeterReading> getLastMonthMeterReading(int tenantId) {
        return meterReadingRepository.getLastMonthMeterReadingByTenantId(tenantId);
    }

    @Override
    public MeterReading updateMeterReading(MeterReading meterReading) {
        return meterReadingRepository.save(meterReading);

    }
    @Override
    public void deleteMeterReading(MeterReading meterReading) {
        meterReadingRepository.delete(meterReading);
    }

}
