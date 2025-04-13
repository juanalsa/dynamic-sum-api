package com.dynamic.dynamic_sum_api.model.dto;

import com.dynamic.dynamic_sum_api.model.entity.CallLog;

import java.time.LocalDateTime;
import java.util.UUID;

public record CallLogDTO(
        UUID id,
        LocalDateTime timestamp,
        String endpoint,
        String parameters,
        String result,
        String status
) {
    public static CallLogDTO fromEntity(CallLog callLog) {
        return new CallLogDTO(
                callLog.getId(),
                callLog.getTimestamp(),
                callLog.getEndpoint(),
                callLog.getParameters(),
                callLog.getResult(),
                callLog.getStatus()
        );
    }
}
