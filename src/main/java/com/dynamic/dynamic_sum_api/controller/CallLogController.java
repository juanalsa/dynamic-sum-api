package com.dynamic.dynamic_sum_api.controller;

import com.dynamic.dynamic_sum_api.model.dto.CallLogDTO;
import com.dynamic.dynamic_sum_api.service.CallLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Historial", description = "Consulta de logs de llamadas a la API")
public class CallLogController {

    private final CallLogService callLogService;

    public CallLogController(CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    @Operation(
            summary = "Consultar historial de llamadas",
            description = "Devuelve el historial de llamadas a la API con paginación y orden descendente por fecha"
    )
    @GetMapping
    public ResponseEntity<Page<CallLogDTO>> getLogs(
            @Parameter(description = "Número de página (empieza desde 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de resultados por página", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Page<CallLogDTO> logs = callLogService.getCallLogs(page, size);
        return ResponseEntity.ok(logs);
    }
}
