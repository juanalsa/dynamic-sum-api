package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CalculationResponse;
import com.dynamic.dynamic_sum_api.service.CalculationService;
import com.dynamic.dynamic_sum_api.service.CallLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Cálculo", description = "Operaciones de suma con porcentaje dinámico")
public class CalculationController {

    private final CalculationService calculationService;
    private final CallLogService callLogService;

    public CalculationController(CalculationService calculationService,
                                 CallLogService callLogService) {
        this.calculationService = calculationService;
        this.callLogService = callLogService;
    }

    @Operation(
            summary = "Cálculo con porcentaje dinámico",
            description = "Suma dos números y aplica un porcentaje adicional obtenido desde un servicio externo. "
                    + "Si el servicio externo falla, se usa el último porcentaje almacenado en caché si está disponible."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado del cálculo exitoso"),
            @ApiResponse(responseCode = "500", description = "Error si el porcentaje no está disponible")
    })
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(
            @Parameter(description = "Primer número", example = "10.0", required = true)
            @RequestParam(name = "num1", required = true) Double num1,

            @Parameter(description = "Segundo número", example = "20.0", required = true)
            @RequestParam(name = "num2", required = true) Double num2) {

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
