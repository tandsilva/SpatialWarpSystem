package com.txt.backend.controller;

import com.txt.backend.model.AlertHistory;
import com.txt.backend.repository.AlertRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Flight Recorder (Black Box)", description = "Access historical data of system alerts and AI responses")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping
    @Operation(summary = "Get All Flight Logs", description = "Retrieves the persistent history of alerts processed by the system.")
    public ResponseEntity<List<AlertHistory>> getAllAlerts() {
        return ResponseEntity.ok(alertRepository.findAll());
    }
}
