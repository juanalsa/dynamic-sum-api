package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CallLogDTO;
import com.dynamic.dynamic_sum_api.service.CallLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CallLogController.class)
class CallLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CallLogService callLogService;

    @Test
    void should_return_logs_when_search_call_logs() throws Exception {
        // Arrange
        CallLogDTO log1 = new CallLogDTO(
                UUID.randomUUID(),
                LocalDateTime.now(),
                "endpoint1",
                "params1",
                "response1",
                "SUCCESS"
        );
        CallLogDTO log2 = new CallLogDTO(
                UUID.randomUUID(),
                LocalDateTime.now(),
                "endpoint2",
                "params2",
                "response2",
                "ERROR"
        );
        Page<CallLogDTO> mockPage = new PageImpl<>(List.of(log1, log2), PageRequest.of(0, 2), 2);

        when(callLogService.getCallLogs(0, 2)).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(get("/api/logs")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].endpoint").value("endpoint1"))
                .andExpect(jsonPath("$.content[0].status").value("SUCCESS"))
                .andExpect(jsonPath("$.content[1].endpoint").value("endpoint2"))
                .andExpect(jsonPath("$.content[1].status").value("ERROR"));
    }

    @Test
    void should_return_empty_logs_when_no_data() throws Exception {
        // Arrange
        Page<CallLogDTO> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 2), 0);

        when(callLogService.getCallLogs(0, 2)).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/api/logs")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}