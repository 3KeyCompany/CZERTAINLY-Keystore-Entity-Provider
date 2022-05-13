package com.czertainly.provider.entity.keystore.service.impl;

import com.czertainly.api.model.common.HealthDto;
import com.czertainly.api.model.common.HealthStatus;
import com.czertainly.provider.entity.keystore.service.HealthCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthCheckServiceImpl implements HealthCheckService {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckServiceImpl.class);

    @Override
    public HealthDto checkHealth() {
        HealthDto health = new HealthDto();
        health.setParts(checkDbStatus());

        // set the overall status
        health.setStatus(HealthStatus.OK);
        for (var entry : health.getParts().entrySet()) {
            if (entry.getValue().getStatus() == HealthStatus.NOK) {
                health.setStatus(HealthStatus.NOK);
                break;
            }
        }
        return health;
    }

    private Map<String, HealthDto> checkDbStatus() {
        Map<String, HealthDto> parts = new HashMap<>();
        HealthDto h = new HealthDto();
//        try {
//            authorityInstanceService.listAuthorityInstances();
//            h.setStatus(HealthStatus.OK);
//            h.setDescription("Database connection ok");
//            parts.put("database", h);
//        } catch (Exception e) {
//            logger.debug("Health check on DB failed: " + e);
//            h.setStatus(HealthStatus.NOK);
//            h.setDescription(e.getMessage());
//            parts.put("database", h);
//        }
        return parts;
    }

}
