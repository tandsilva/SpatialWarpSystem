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
 * REST controller for spaceship operations and monitoring.
 * Provides endpoints for ship status, warp simulation, and system checks.
 */
@RestController
@RequestMapping("/api/ship")
@Tag(name = "Ship Management", description = "Operations for managing spaceship systems")
public class ShipController {

    private static final Logger logger = LoggerFactory.getLogger(ShipController.class);

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    /**
         * Retrieves the comprehensive ship status including all systems.
     */
    @PostMapping("/status")
        @Operation(summary = "Get ship status", description = "Returns detailed status for all ship systems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
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
         * Simulates warp drive operation.
     */
    @PostMapping("/warp/simulate")
        @Operation(summary = "Simulate warp drive", description = "Calculates warp parameters for space travel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warp simulated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid warp parameters")
    })
    public ResponseEntity<warpSimulationResponse> simulateWarp(@Valid @RequestBody WarpSimulationRequest request) {
        logger.info("Simulating warp drive with velocity: {}", request.bubbleVelocity());
        warpSimulationResponse simulation = shipService.simulateWarp(request);
        return ResponseEntity.ok(simulation);
    }

    /**
         * Checks whether the current speed is within safety limits.
     */
    @PostMapping("/speed/check")
        @Operation(summary = "Check speed limit", description = "Validates whether the current speed is safe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Speed checked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid speed value")
    })
    public ResponseEntity<String> checkSpeed(@Valid @RequestBody SpeedCheckRequest request) {
        logger.debug("Checking speed: {}", request.currentSpeed());
        String result = shipService.checkSpeedLimit(request.currentSpeed());
        return ResponseEntity.ok(result);
    }

    /**
         * Monitors oxygen and CO2 levels in the atmosphere.
     */
    @PostMapping("/atmosphere/monitor")
        @Operation(summary = "Monitor atmosphere", description = "Checks oxygen and carbon dioxide levels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atmosphere monitored successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid atmosphere parameters")
    })
    public ResponseEntity<String> monitorAtmosphere(@Valid @RequestBody AtmosphereCheckRequest request) {
        logger.debug("Monitoring atmosphere - O2: {}, CO2: {}", request.oxygenLevel(), request.co2Level());
        String status = shipService.monitorAtmosphere(request.oxygenLevel(), request.co2Level());
        return ResponseEntity.ok(status);
    }

    /**
         * Prioritizes energy systems based on the current level.
     */
    @GetMapping("/energy/prioritize")
        @Operation(summary = "Prioritize energy", description = "Determines which systems should be prioritized")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Energy prioritized successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid energy level")
    })
    public ResponseEntity<String> prioritizeEnergy(@RequestParam Double currentEnergy) {
        logger.debug("Prioritizing energy with level: {}", currentEnergy);
        String priority = shipService.prioritizeEnergyUsage(currentEnergy);
        return ResponseEntity.ok(priority);
    }

    /**
         * Runs AI-based failure detection analysis.
     */
    @PostMapping("/ai/detect-failures")
        @Operation(summary = "Detect failures", description = "Uses AI to detect system failures")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Failure detection executed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid system parameters")
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