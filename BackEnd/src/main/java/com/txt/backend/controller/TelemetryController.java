package com.txt.backend.controller;

import com.txt.backend.service.AtmosphereService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telemetry")
@Tag(name = "Telemetry & Sensors", description = "Endpoints to simulate sensor readings and test RabbitMQ alerts")
public class TelemetryController {

    private final AtmosphereService atmosphereService;

    public TelemetryController(AtmosphereService atmosphereService) {
        this.atmosphereService = atmosphereService;
    }

    @PostMapping("/simulate/oxygen")
    @Operation(summary = "Inject Oxygen Reading", description = "Simulates a sensor reading. If monitoring < 19.5%, it triggers a RabbitMQ Alert.")
    public ResponseEntity<String> simulateOxygenLevel(@RequestParam double oxygenPercentage) {
        
        // This calls the service logic we created earlier.
        // If oxygen < 19.5, it will publish to RabbitMQ.
        String status = atmosphereService.monitorOxygenLevel(oxygenPercentage, 0.04);
        
        return ResponseEntity.ok("Sensor Reading Processed. System Status: " + status);
    }
}
