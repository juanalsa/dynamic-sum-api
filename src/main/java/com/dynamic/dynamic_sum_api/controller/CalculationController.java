package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import com.dynamic.dynamic_sum_api.service.CalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalculationController {

    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(
            @RequestParam double num1,
            @RequestParam double num2) {

        CalculationResponse response = calculationService.calculate(num1, num2);
        return ResponseEntity.ok(response);
    }
}
