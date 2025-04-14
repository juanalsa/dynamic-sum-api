package com.dynamic.dynamic_sum_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con el resultado del cálculo y porcentaje aplicado")
public record CalculationResponse(
        @Schema(description = "Resultado final después de aplicar el porcentaje", example = "33.0")
        double result,
        @Schema(description = "Porcentaje aplicado a la suma", example = "10.0")
        double percentageApplied) {
}
