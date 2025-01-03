package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.WaterConsumptionLog;
import pbs.edu.cooperative.repository.WaterConsumptionLogRepository;
import pbs.edu.cooperative.service.WaterConsumptionLogService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WaterConsumptionLogServiceImpl implements WaterConsumptionLogService {

    private final WaterConsumptionLogRepository logRepository;

    @Autowired
    public WaterConsumptionLogServiceImpl(WaterConsumptionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public WaterConsumptionLog saveLog(WaterConsumptionLog log) {
        Optional<WaterConsumptionLog> firstLogOpt = logRepository
                .findFirstByTenantIdAndConsumptionDateMonth(log.getTenant().getTenantId(), log.getConsumptionDate().getMonthValue());
        float defaultMeterReading = firstLogOpt.map(WaterConsumptionLog::getMeterReading).orElse(0.0f);
        log.setMeterReading(defaultMeterReading);
        return logRepository.save(log);
    }

    @Override
    public List<WaterConsumptionLog> getLogsByTenantId(int tenantId) {
        return logRepository.findByTenantTenantId(tenantId);
    }
}