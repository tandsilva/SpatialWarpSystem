package com.txt.backend.controller;

import com.txt.backend.dto.*;
import com.txt.backend.service.ShipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for spaceship operations and monitoring.
 * Provides endpoints for ship status, warp simulation, and system checks.
 */
@RestController
@RequestMapping("/api/ship")
@Api(tags = "Ship Management", description = "Operations for managing spaceship systems")
public class ShipController {

    private static final Logger logger = LoggerFactory.getLogger(ShipController.class);

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    /**
     * Get comprehensive ship status including all systems
     */
    @PostMapping("/status")
    @ApiOperation(value = "Get comprehensive ship status", notes = "Returns detailed status of all ship systems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved ship status"),
            @ApiResponse(code = 400, message = "Invalid request parameters")
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
     * Simulate warp drive operation
     */
    @PostMapping("/warp/simulate")
    @ApiOperation(value = "Simulate warp drive", notes = "Calculates warp parameters for space travel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully simulated warp drive"),
            @ApiResponse(code = 400, message = "Invalid warp parameters")
    })
    public ResponseEntity<warpSimulationResponse> simulateWarp(@Valid @RequestBody WarpSimulationRequest request) {
        logger.info("Simulating warp drive with velocity: {}", request.bubbleVelocity());
        warpSimulationResponse simulation = shipService.simulateWarp(request);
        return ResponseEntity.ok(simulation);
    }

    /**
     * Check if current speed is within safe limits
     */
    @PostMapping("/speed/check")
    @ApiOperation(value = "Check speed limits", notes = "Validates if current speed is safe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked speed"),
            @ApiResponse(code = 400, message = "Invalid speed value")
    })
    public ResponseEntity<String> checkSpeed(@Valid @RequestBody SpeedCheckRequest request) {
        logger.debug("Checking speed: {}", request.currentSpeed());
        String result = shipService.checkSpeedLimit(request.currentSpeed());
        return ResponseEntity.ok(result);
    }

    /**
     * Monitor atmosphere oxygen and CO2 levels
     */
    @PostMapping("/atmosphere/monitor")
    @ApiOperation(value = "Monitor atmosphere", notes = "Checks oxygen and CO2 levels")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully monitored atmosphere"),
            @ApiResponse(code = 400, message = "Invalid atmosphere parameters")
    })
    public ResponseEntity<String> monitorAtmosphere(@Valid @RequestBody AtmosphereCheckRequest request) {
        logger.debug("Monitoring atmosphere - O2: {}, CO2: {}", request.oxygenLevel(), request.co2Level());
        String status = shipService.monitorAtmosphere(request.oxygenLevel(), request.co2Level());
        return ResponseEntity.ok(status);
    }

    /**
     * Prioritize energy systems based on current energy level
     */
    @GetMapping("/energy/prioritize")
    @ApiOperation(value = "Prioritize energy", notes = "Determines which systems should be prioritized")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully prioritized energy"),
            @ApiResponse(code = 400, message = "Invalid energy level")
    })
    public ResponseEntity<String> prioritizeEnergy(@RequestParam Double currentEnergy) {
        logger.debug("Prioritizing energy with level: {}", currentEnergy);
        String priority = shipService.prioritizeEnergyUsage(currentEnergy);
        return ResponseEntity.ok(priority);
    }

    /**
     * Run AI failure detection analysis
     */
    @PostMapping("/ai/detect-failures")
    @ApiOperation(value = "Detect failures", notes = "Uses AI to detect system failures")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully ran failure detection"),
            @ApiResponse(code = 400, message = "Invalid system parameters")
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