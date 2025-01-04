package pbs.edu.cooperative.service;

import pbs.edu.cooperative.model.WaterConsumptionLog;

import java.util.List;
import java.util.Optional;

public interface WaterConsumptionLogService {
    WaterConsumptionLog saveLog(WaterConsumptionLog log);
    List<WaterConsumptionLog> getLogsByTenantId(int tenantId);
    Optional<WaterConsumptionLog> getLastMonthLogByTenantId(int tenantId);
}
