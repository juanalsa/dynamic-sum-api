package com.dynamic.dynamic_sum_api.service;

import com.dynamic.dynamic_sum_api.model.dto.CallLogDTO;
import com.dynamic.dynamic_sum_api.model.entity.CallLog;
import com.dynamic.dynamic_sum_api.repository.CallLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CallLogServiceTest {

    @Mock
    private CallLogRepository callLogRepository;

    @InjectMocks
    private CallLogService callLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Disabled("Reason: Pending error validation")
    void should_save_call_log_when_parameters_are_valid() {
        // Arrange
        String endpoint = "/api/calculate";
        String params = "num1=10, num2=20";
        String result = "{ result: 33.0 }";
        String status = "SUCCESS";

        // Act
        callLogService.logCall(endpoint, params, result, status);

        ArgumentCaptor<CallLog> captor = ArgumentCaptor.forClass(CallLog.class);
        verify(callLogRepository, timeout(1000).times(1)).save(captor.capture());

        CallLog savedLog = captor.getValue();
        assertEquals(endpoint, savedLog.getEndpoint());
        assertEquals(params, savedLog.getParameters());
        assertEquals(result, savedLog.getResult());
        assertEquals(status, savedLog.getStatus());
        assertNotNull(savedLog.getTimestamp());
    }

    @Test
    @Disabled("Reason: Pending error validation")
    void should_return_paged_call_logs_when_get_call_logs() {
        // Arrange
        int page = 0;
        int size = 10;
        CallLog callLog = new CallLog();
        callLog.setTimestamp(LocalDateTime.now());
        callLog.setEndpoint("/api/test");
        callLog.setParameters("param1=value1");
        callLog.setResult("Success");
        callLog.setStatus("SUCCESS");

        Page<CallLog> mockPage = new PageImpl<>(List.of(callLog));
        when(callLogRepository.findAll(PageRequest.of(page, size, Sort.by("timestamp").descending())))
                .thenReturn(mockPage);

        // Act
        Page<CallLogDTO> result = callLogService.getCallLogs(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("/api/test", result.getContent().get(0).endpoint());
        verify(callLogRepository, times(1))
                .findAll(PageRequest.of(page, size, Sort.by("timestamp").descending()));

    }
}