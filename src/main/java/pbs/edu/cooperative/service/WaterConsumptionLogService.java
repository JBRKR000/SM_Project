package pbs.edu.cooperative.service;

import pbs.edu.cooperative.model.WaterConsumptionLog;

import java.util.List;

public interface WaterConsumptionLogService {
    WaterConsumptionLog saveLog(WaterConsumptionLog log);
    List<WaterConsumptionLog> getLogsByTenantId(int tenantId);
}
