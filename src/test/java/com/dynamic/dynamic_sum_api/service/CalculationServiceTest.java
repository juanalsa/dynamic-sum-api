package com.dynamic.dynamic_sum_api.service;

import com.dynamic.dynamic_sum_api.exception.PercentageUnavailableException;
import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    @Mock
    private ExternalPercentageService percentageService;

    @InjectMocks
    private CalculationService calculationService;

    @Test
    void should_return_calculation_when_external_percentage_works() {
        when(percentageService.getPercentage()).thenReturn(10.0);

        CalculationResponse response = calculationService.calculate(10, 20);

        assertEquals(33.0, response.result());
        assertEquals(10.0, response.percentageApplied());
    }

    @Test
    void should_use_cached_percentage_when_external_percentage_fails() {
        when(percentageService.getPercentage()).thenReturn(15.0);
        calculationService.calculate(10, 10);

        when(percentageService.getPercentage()).thenThrow(new RuntimeException("Service down"));

        CalculationResponse response = calculationService.calculate(10, 10);
        assertEquals(23.0, response.result());
    }

    @Test
    void should_throw_percentage_unavailable_exception_when_no_cached_value() {
        when(percentageService.getPercentage()).thenThrow(new RuntimeException("Service failed"));

        PercentageUnavailableException ex = assertThrows(
                PercentageUnavailableException.class,
                () -> calculationService.calculate(5, 5)
        );

        assertEquals("No cached percentage available and external service failed.", ex.getMessage());
    }
}