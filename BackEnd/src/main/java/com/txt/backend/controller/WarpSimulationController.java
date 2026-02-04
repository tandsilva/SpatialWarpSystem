package com.txt.backend.controller;

import com.txt.backend.dto.WarpSimulationRequest;
import com.txt.backend.dto.warpSimulationResponse;
import com.txt.backend.mapper.WarpSimulationMapper;
import com.txt.backend.model.WarpSimulation;
import com.txt.backend.service.WarpSimulationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warp")
public class WarpSimulationController {

    private final WarpSimulationService warpSimulationService;

    public WarpSimulationController(WarpSimulationService warpSimulationService) {
        this.warpSimulationService = warpSimulationService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<warpSimulationResponse> simulateWarp(@Valid @RequestBody WarpSimulationRequest request) {
        WarpSimulation simulation = warpSimulationService.simulateWarp(
                request.initialX(),
                request.initialY(),
                request.initialZ(),
                request.finalX(),
                request.finalY(),
                request.finalZ(),
                request.bubbleVelocity()
        );
        return ResponseEntity.ok(WarpSimulationMapper.toResponse(simulation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<warpSimulationResponse> getById(@PathVariable Long id) {
        WarpSimulation simulation = warpSimulationService.getById(id);
        return ResponseEntity.ok(WarpSimulationMapper.toResponse(simulation));
    }

    @GetMapping
    public ResponseEntity<List<warpSimulationResponse>> getAll() {
        List<warpSimulationResponse> simulations = warpSimulationService.getAll().stream()
                .map(WarpSimulationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(simulations);
    }
}
