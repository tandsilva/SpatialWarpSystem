package com.txt.backend.controller;

import com.txt.backend.dto.QuarantineRequest;
import com.txt.backend.dto.QuarantineResponse;
import com.txt.backend.mapper.QuarantineMapper;
import com.txt.backend.model.Quarantine;
import com.txt.backend.service.QuarantineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for quarantine management.
 * Handles emergency protocols and user isolation.
 */
@RestController
@RequestMapping("/api/quarantines")
@Api(tags = "Quarantine Management", description = "Operations for managing emergency quarantines")
public class QuarantineController {

    private static final Logger logger = LoggerFactory.getLogger(QuarantineController.class);

    private final QuarantineService quarantineService;

    public QuarantineController(QuarantineService quarantineService) {
        this.quarantineService = quarantineService;
    }

    @PostMapping
    @ApiOperation(value = "Start a new quarantine", notes = "Initiates a new quarantine with specified emergency protocol")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quarantine started successfully"),
            @ApiResponse(code = 400, message = "Invalid quarantine parameters"),
            @ApiResponse(code = 422, message = "Quarantine code already exists")
    })
    public ResponseEntity<QuarantineResponse> startQuarantine(@Valid @RequestBody QuarantineRequest request) {
        logger.info("Starting quarantine with code: {}", request.codeNumber());
        Quarantine quarantine = QuarantineMapper.toEntity(request);
        Quarantine created = quarantineService.startQuarantine(quarantine, request.userIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(QuarantineMapper.toResponse(created));
    }

    @GetMapping("/{codeNumber}")
    @ApiOperation(value = "Get quarantine by code", notes = "Retrieves quarantine details by code number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved quarantine"),
            @ApiResponse(code = 404, message = "Quarantine not found")
    })
    public ResponseEntity<QuarantineResponse> getByCode(@PathVariable String codeNumber) {
        logger.debug("Fetching quarantine: {}", codeNumber);
        Quarantine quarantine = quarantineService.getQuarantineByCode(codeNumber);
        return ResponseEntity.ok(QuarantineMapper.toResponse(quarantine));
    }

    @GetMapping
    @ApiOperation(value = "Get all quarantines", notes = "Retrieves all quarantines in the system")
    @ApiResponse(code = 200, message = "Successfully retrieved all quarantines")
    public ResponseEntity<List<QuarantineResponse>> getAll() {
        logger.debug("Fetching all quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @GetMapping("/active")
    @ApiOperation(value = "Get all active quarantines", notes = "Retrieves only active quarantines")
    @ApiResponse(code = 200, message = "Successfully retrieved active quarantines")
    public ResponseEntity<List<QuarantineResponse>> getAllActive() {
        logger.debug("Fetching all active quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllActiveQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @PostMapping("/{codeNumber}/end")
    @ApiOperation(value = "End quarantine", notes = "Ends a quarantine if the protocol allows")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Quarantine ended successfully"),
            @ApiResponse(code = 404, message = "Quarantine not found"),
            @ApiResponse(code = 422, message = "Quarantine cannot be interrupted")
    })
    public ResponseEntity<Void> endQuarantine(@PathVariable String codeNumber) {
        logger.info("Ending quarantine: {}", codeNumber);
        quarantineService.endQuarantine(codeNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "Check if user is in quarantine", notes = "Verifies if a specific user is currently in quarantine")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully checked user status"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public ResponseEntity<Boolean> isUserInQuarantine(@PathVariable Long userId) {
        logger.debug("Checking quarantine status for user: {}", userId);
        return ResponseEntity.ok(quarantineService.isUserInQuarantine(userId));
    }
}
