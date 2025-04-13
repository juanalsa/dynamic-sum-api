package com.dynamic.dynamic_sum_api.service;

import com.dynamic.dynamic_sum_api.model.entity.CallLog;
import com.dynamic.dynamic_sum_api.repository.CallLogRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CallLogService {

    private final CallLogRepository callLogRepository;

    public CallLogService(CallLogRepository callLogRepository) {
        this.callLogRepository = callLogRepository;
    }

    @Async
    public void logCall(String endpoint, String params, String result, String status) {
        CallLog callLog = new CallLog();
        callLog.setTimestamp(LocalDateTime.now());
        callLog.setEndpoint(endpoint);
        callLog.setParameters(params);
        callLog.setResult(result);
        callLog.setStatus(status);

        callLogRepository.save(callLog);
    }
}
