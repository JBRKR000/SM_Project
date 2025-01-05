package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.MeterReading;
import pbs.edu.cooperative.model.WaterConsumptionLog;
import pbs.edu.cooperative.repository.MeterReadingRepository;
import pbs.edu.cooperative.repository.WaterConsumptionLogRepository;
import pbs.edu.cooperative.service.WaterConsumptionLogService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class WaterConsumptionLogServiceImpl implements WaterConsumptionLogService {

    private final WaterConsumptionLogRepository logRepository;
    private final MeterReadingRepository meterReadingRepository;

    @Autowired
    public WaterConsumptionLogServiceImpl(WaterConsumptionLogRepository logRepository, MeterReadingRepository meterReadingRepository) {
        this.logRepository = logRepository;
        this.meterReadingRepository = meterReadingRepository;
    }

    public WaterConsumptionLog saveLog(WaterConsumptionLog log) {
        return logRepository.save(log);
    }

    @Override
    public List<WaterConsumptionLog> getLogsByTenantId(int tenantId) {
        return logRepository.findByTenantTenantId(tenantId);
    }

    @Override
    public Optional<WaterConsumptionLog> getLastMonthLogByTenantId(int tenantId) {
        YearMonth lastMonth = YearMonth.now().minusMonths(1);
        LocalDate startDate = lastMonth.atDay(1);
        LocalDate endDate = lastMonth.atEndOfMonth().plusDays(1);
        return logRepository.findLastLogByTenantIdAndLastMonth(tenantId, startDate, endDate);
    }
}