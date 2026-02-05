package com.txt.backend.controller;

import com.txt.backend.dto.*;
import com.txt.backend.service.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para operações e monitoramento da nave espacial.
 * Fornece endpoints para status da nave, simulação de dobra e verificações de sistema.
 */
@RestController
@RequestMapping("/api/ship")
@Tag(name = "Gestão da Nave", description = "Operações para gerenciar os sistemas da nave espacial")
public class ShipController {

    private static final Logger logger = LoggerFactory.getLogger(ShipController.class);

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    /**
     * Obtém o status abrangente da nave incluindo todos os sistemas.
     */
    @PostMapping("/status")
    @Operation(summary = "Obter status da nave", description = "Retorna o status detalhado de todos os sistemas da nave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status recuperado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de requisição inválidos")
    })
    public ResponseEntity<ShipStatusResponse> getShipStatus(@Valid @RequestBody ShipStatusRequest request) {
        logger.info("Received ship status request");
        ShipStatusResponse status = shipService.getComprehensiveStatus(
                request.oxygenLevel(),
                request.co2Level(),
                request.energyLevel(),
                request.hullIntegrity(),
                request.currentSpeed()
        );
        return ResponseEntity.ok(status);
    }

    /**
     * Simula a operação do motor de dobra.
     */
    @PostMapping("/warp/simulate")
    @Operation(summary = "Simular motor de dobra", description = "Calcula parâmetros de dobra para viagem espacial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dobra simulada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de dobra inválidos")
    })
    public ResponseEntity<warpSimulationResponse> simulateWarp(@Valid @RequestBody WarpSimulationRequest request) {
        logger.info("Simulating warp drive with velocity: {}", request.bubbleVelocity());
        warpSimulationResponse simulation = shipService.simulateWarp(request);
        return ResponseEntity.ok(simulation);
    }

    /**
     * Verifica se a velocidade atual está dentro dos limites de segurança.
     */
    @PostMapping("/speed/check")
    @Operation(summary = "Verificar limite de velocidade", description = "Valida se a velocidade atual é segura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Velocidade verificada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor de velocidade inválido")
    })
    public ResponseEntity<String> checkSpeed(@Valid @RequestBody SpeedCheckRequest request) {
        logger.debug("Checking speed: {}", request.currentSpeed());
        String result = shipService.checkSpeedLimit(request.currentSpeed());
        return ResponseEntity.ok(result);
    }

    /**
     * Monitora os níveis de oxigênio e CO2 da atmosfera.
     */
    @PostMapping("/atmosphere/monitor")
    @Operation(summary = "Monitorar atmosfera", description = "Verifica níveis de oxigênio e dióxido de carbono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atmosfera monitorada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de atmosfera inválidos")
    })
    public ResponseEntity<String> monitorAtmosphere(@Valid @RequestBody AtmosphereCheckRequest request) {
        logger.debug("Monitoring atmosphere - O2: {}, CO2: {}", request.oxygenLevel(), request.co2Level());
        String status = shipService.monitorAtmosphere(request.oxygenLevel(), request.co2Level());
        return ResponseEntity.ok(status);
    }

    /**
     * Prioriza sistemas de energia baseado no nível atual.
     */
    @GetMapping("/energy/prioritize")
    @Operation(summary = "Priorizar energia", description = "Determina quais sistemas devem ser priorizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Energia priorizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nível de energia inválido")
    })
    public ResponseEntity<String> prioritizeEnergy(@RequestParam Double currentEnergy) {
        logger.debug("Prioritizing energy with level: {}", currentEnergy);
        String priority = shipService.prioritizeEnergyUsage(currentEnergy);
        return ResponseEntity.ok(priority);
    }

    /**
     * Executa análise de detecção de falhas via IA.
     */
    @PostMapping("/ai/detect-failures")
    @Operation(summary = "Detectar falhas", description = "Usa IA para detectar falhas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detecção de falhas executada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de sistema inválidos")
    })
    public ResponseEntity<String> detectFailures(@Valid @RequestBody ShipStatusRequest request) {
        logger.info("Running AI failure detection");
        String report = shipService.runFailureDetection(
                request.oxygenLevel(),
                request.energyLevel(),
                request.hullIntegrity()
        );
        return ResponseEntity.ok(report);
    }
}