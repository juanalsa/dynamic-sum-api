package com.dynamic.dynamic_sum_api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExternalPercentageServiceTest {

    @Mock
    private Random random;

    @InjectMocks
    private ExternalPercentageService externalPercentageService;

    @Test
    void should_return_percentage_when_random_is_true() {
        // Arrange
        when(random.nextBoolean()).thenReturn(true);

        // Act
        double result = externalPercentageService.getPercentage();

        // Assert
        assertEquals(10.0, result, 0.001);
    }

    @Test
    void should_throw_runtime_exception_when_random_is_false() {
        // Arrange
        when(random.nextBoolean()).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> externalPercentageService.getPercentage());

        assertEquals("External service failed", exception.getMessage());
    }
}