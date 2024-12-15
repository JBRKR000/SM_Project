package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.WaterConsumptionLog;
import pbs.edu.cooperative.repository.WaterConsumptionLogRepository;
import pbs.edu.cooperative.service.WaterConsumptionLogService;

import java.util.List;

@Service
public class WaterConsumptionLogServiceImpl implements WaterConsumptionLogService {

    private final WaterConsumptionLogRepository logRepository;

    @Autowired
    public WaterConsumptionLogServiceImpl(WaterConsumptionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public WaterConsumptionLog saveLog(WaterConsumptionLog log) {
        return logRepository.save(log);
    }

    @Override
    public List<WaterConsumptionLog> getLogsByTenantId(int tenantId) {
        return logRepository.findByTenantTenantId(tenantId);
    }
}
