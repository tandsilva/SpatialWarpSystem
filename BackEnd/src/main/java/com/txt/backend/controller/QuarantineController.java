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
 * Controller REST para gestão de quarentena.
 * Lida com protocolos de emergência e isolamento de usuários.
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
    @Operation(summary = "Iniciar uma nova quarentena", description = "Inicia uma nova quarentena com o protocolo de emergência especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quarentena iniciada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros de quarentena inválidos"),
            @ApiResponse(responseCode = "422", description = "Código de quarentena já existe")
    })
    public ResponseEntity<QuarantineResponse> startQuarantine(@Valid @RequestBody QuarantineRequest request) {
        logger.info("Starting quarantine with code: {}", request.codeNumber());
        Quarantine quarantine = QuarantineMapper.toEntity(request);
        Quarantine created = quarantineService.startQuarantine(quarantine, request.userIds());
        return ResponseEntity.status(HttpStatus.CREATED).body(QuarantineMapper.toResponse(created));
    }

    @GetMapping("/{codeNumber}")
    @Operation(summary = "Obter quarentena por código", description = "Recupera detalhes da quarentena pelo código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quarentena recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quarentena não encontrada")
    })
    public ResponseEntity<QuarantineResponse> getByCode(@PathVariable String codeNumber) {
        logger.debug("Fetching quarantine: {}", codeNumber);
        Quarantine quarantine = quarantineService.getQuarantineByCode(codeNumber);
        return ResponseEntity.ok(QuarantineMapper.toResponse(quarantine));
    }

    @GetMapping
    @Operation(summary = "Listar todas as quarentenas", description = "Recupera todas as quarentenas do sistema")
    @ApiResponse(responseCode = "200", description = "Todas as quarentenas recuperadas com sucesso")
    public ResponseEntity<List<QuarantineResponse>> getAll() {
        logger.debug("Fetching all quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @GetMapping("/active")
    @Operation(summary = "Listar quarentenas ativas", description = "Recupera apenas as quarentenas ativas")
    @ApiResponse(responseCode = "200", description = "Quarentenas ativas recuperadas com sucesso")
    public ResponseEntity<List<QuarantineResponse>> getAllActive() {
        logger.debug("Fetching all active quarantines");
        List<QuarantineResponse> quarantines = quarantineService.getAllActiveQuarantines().stream()
                .map(QuarantineMapper::toResponse)
                .toList();
        return ResponseEntity.ok(quarantines);
    }

    @PostMapping("/{codeNumber}/end")
    @Operation(summary = "Encerrar quarentena", description = "Encerra uma quarentena se o protocolo permitir")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Quarentena encerrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Quarentena não encontrada"),
            @ApiResponse(responseCode = "422", description = "Quarentena não pode ser interrompida")
    })
    public ResponseEntity<Void> endQuarantine(@PathVariable String codeNumber) {
        logger.info("Ending quarantine: {}", codeNumber);
        quarantineService.endQuarantine(codeNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Verificar usuário em quarentena", description = "Verifica se um usuário específico está atualmente em quarentena")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do usuário verificado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Boolean> isUserInQuarantine(@PathVariable Long userId) {
        logger.debug("Checking quarantine status for user: {}", userId);
        return ResponseEntity.ok(quarantineService.isUserInQuarantine(userId));
    }
}
