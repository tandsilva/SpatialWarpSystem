package com.txt.backend.controller;

import com.txt.backend.dto.QuarantineRequest;
import com.txt.backend.dto.QuarantineResponse;
import com.txt.backend.mapper.QuarantineMapper;
import com.txt.backend.model.Quarantine;
import com.txt.backend.service.QuarantineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for quarantine management.
 * Handles emergency protocols and user isolation.
 */
@RestController
@RequestMapping("/api/quarantines")
@Tag(name = "Gestão de Quarentena", description = "Operações para gerenciar quarentenas de emergência")
public class QuarantineController {

    private static final Logger logger = LoggerFactory.getLogger(QuarantineController.class);

    private final QuarantineService quarantineService;

    public QuarantineController(QuarantineService quarantineService) {
        this.quarantineService = quarantineService;
    }

    @PostMapping
        @Operation(summary = "Start a new quarantine", description = "Starts a new quarantine with the specified emergency protocol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quarantine started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quarantine parameters"),
            @ApiResponse(responseCode = "422", description = "Quarantine code already exists")
    })
    public ResponseEntity<QuarantineResponse> startQuarantine(@Valid @RequestBody QuarantineRequest request) {
        logger.info("Starting quarantine with code: {}", request.codeNumber());
        Quarantine quarantine = QuarantineMapper.toEntity(request);
        Quarantine created = quarantineService.startQuarantine(quarantine, request.userIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(QuarantineMapper.toResponse(created));
    }

    @GetMapping("/{codeNumber}")
        @Operation(summary = "Get quarantine by code", description = "Retrieves quarantine details by code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarantine retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Quarantine not found")
    })
    public ResponseEntity<QuarantineResponse> getByCode(@PathVariable String codeNumber) {
        logger.debug("Fetching quarantine: {}", codeNumber);
        Quarantine quarantine = quarantineService.getQuarantineByCode(codeNumber);
        return ResponseEntity.ok(QuarantineMapper.toResponse(quarantine));
    }

    @GetMapping
    @Operation(summary = "List all quarantines", description = "Retrieves all quarantines in the system")
    @ApiResponse(responseCode = "200", description = "All quarantines retrieved successfully")
    public ResponseEntity<List<QuarantineResponse>> getAll() {
        logger.debug("Fetching all quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @GetMapping("/active")
    @Operation(summary = "List active quarantines", description = "Retrieves only active quarantines")
    @ApiResponse(responseCode = "200", description = "Active quarantines retrieved successfully")
    public ResponseEntity<List<QuarantineResponse>> getAllActive() {
        logger.debug("Fetching all active quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllActiveQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @PostMapping("/{codeNumber}/end")
        @Operation(summary = "End quarantine", description = "Ends a quarantine if the protocol allows it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quarantine ended successfully"),
            @ApiResponse(responseCode = "404", description = "Quarantine not found"),
            @ApiResponse(responseCode = "422", description = "Quarantine cannot be interrupted")
    })
    public ResponseEntity<Void> endQuarantine(@PathVariable String codeNumber) {
        logger.info("Ending quarantine: {}", codeNumber);
        quarantineService.endQuarantine(codeNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
        @Operation(summary = "Check user quarantine status", description = "Checks whether a specific user is currently in quarantine")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status checked successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Boolean> isUserInQuarantine(@PathVariable Long userId) {
        logger.debug("Checking quarantine status for user: {}", userId);
        return ResponseEntity.ok(quarantineService.isUserInQuarantine(userId));
    }
}
