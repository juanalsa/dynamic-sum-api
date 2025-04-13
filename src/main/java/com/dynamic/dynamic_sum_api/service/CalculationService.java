package com.dynamic.dynamic_sum_api.service;

import com.dynamic.dynamic_sum_api.exception.PercentageUnavailableException;
import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {

    private final ExternalPercentageService percentageService;
    private Double cachedPercentage = null;

    public CalculationService(ExternalPercentageService percentageService) {
        this.percentageService = percentageService;
    }

    public CalculationResponse calculate(double num1, double num2) {
        double sum = num1 + num2;
        double percentage;

        try {
            percentage = percentageService.getPercentage();
            cachedPercentage = percentage;
        } catch (Exception ex) {
            if (cachedPercentage != null) {
                percentage = cachedPercentage;
            } else {
                throw new PercentageUnavailableException("No cached percentage available and external service failed.");
            }
        }

        double result = sum + (sum * percentage / 100);
        return new CalculationResponse(result, percentage);
    }
}
