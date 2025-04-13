package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import com.dynamic.dynamic_sum_api.service.CalculationService;
import com.dynamic.dynamic_sum_api.service.CallLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalculationController {

    private final CalculationService calculationService;
    private final CallLogService callLogService;

    public CalculationController(CalculationService calculationService,
                                 CallLogService callLogService) {
        this.calculationService = calculationService;
        this.callLogService = callLogService;
    }

    @GetMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(
            @RequestParam double num1,
            @RequestParam double num2) {

        String endpoint = "/api/calculate";
        String params = "num1=" + num1 + ", num2=" + num2;

        try {
            CalculationResponse response = calculationService.calculate(num1, num2);
            callLogService.logCall(endpoint, params, response.toString(), "SUCCESS");

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            callLogService.logCall(endpoint, params, ex.getMessage(), "ERROR");
            throw ex;
        }

    }
}
