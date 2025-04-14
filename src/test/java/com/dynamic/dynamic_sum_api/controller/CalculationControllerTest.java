package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import com.dynamic.dynamic_sum_api.service.CalculationService;
import com.dynamic.dynamic_sum_api.service.CallLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculationController.class)
class CalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculationService calculationService;

    @MockitoBean
    private CallLogService callLogService;

    @Test
    void should_return_200_when_register_successful() throws Exception {
        // Arrange
        CalculationResponse mockResponse = new CalculationResponse(33.0, 10.0);

        when(calculationService.calculate(10, 20)).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/calculate")
                        .param("num1", "10")
                        .param("num2", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(33.0))
                .andExpect(jsonPath("$.percentageApplied").value(10.0));
    }

    @Test
    void should_return_400_when_calculation_throws_exception() throws Exception {
        // Arrange
        doThrow(new IllegalArgumentException("Invalid numbers"))
                .when(calculationService)
                .calculate(10.0, 20.0);

        doNothing()
                .when(callLogService)
                .logCall(anyString(), anyString(), anyString(), eq("ERROR"));

        // Act & Assert
        mockMvc.perform(post("/api/calculate")
                        .param("num1", "10")
                        .param("num2", "20"))
                .andExpect(status().isBadRequest());

        verify(callLogService).logCall(
                "/api/calculate",
                "num1=10.0, num2=20.0",
                "Invalid numbers",
                "ERROR"
        );
    }
}