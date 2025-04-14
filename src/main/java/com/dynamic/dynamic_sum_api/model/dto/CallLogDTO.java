package com.dynamic.dynamic_sum_api.model.dto;

import com.dynamic.dynamic_sum_api.model.entity.CallLog;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Detalle de una llamada registrada a la API")
public record CallLogDTO(
        @Schema(description = "ID único de la llamada")
        UUID id,

        @Schema(description = "Fecha y hora de la llamada", example = "2025-04-11T12:45:00")
        LocalDateTime timestamp,

        @Schema(description = "Endpoint invocado", example = "/api/calculate")
        String endpoint,

        @Schema(description = "Parámetros usados en la llamada", example = "num1=5, num2=3")
        String parameters,

        @Schema(description = "Resultado devuelto o mensaje de error")
        String result,

        @Schema(description = "Estado de la llamada", example = "SUCCESS")
        String status
) {
    public static CallLogDTO fromEntity(CallLog callLog) {
        return new CallLogDTO(
                callLog.getId(),
                callLog.getTimestamp(),
                callLog.getEndpoint(),
                callLog.getParameters(),
                callLog.getResult(),
                callLog.getStatus()
        );
    }
}
