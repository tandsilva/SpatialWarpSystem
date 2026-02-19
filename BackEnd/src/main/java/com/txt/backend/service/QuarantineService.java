package com.txt.backend.service;

import com.txt.backend.exception.QuarantineException;
import com.txt.backend.exception.ResourceNotFoundException;
import com.txt.backend.model.Quarantine;
import com.txt.backend.model.User;
import com.txt.backend.repository.QuarantineRepository;
import com.txt.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for managing quarantine operations.
 * Handles quarantine creation, monitoring, and lifecycle management.
 */
@Service
public class QuarantineService {

    private static final Logger logger = LoggerFactory.getLogger(QuarantineService.class);

    private final QuarantineRepository quarantineRepository;
    private final UserRepository userRepository;

    public QuarantineService(QuarantineRepository quarantineRepository, UserRepository userRepository) {
        this.quarantineRepository = quarantineRepository;
        this.userRepository = userRepository;
    }

    /**
     * Start a new quarantine with specified users
     */
    @Transactional
    public Quarantine startQuarantine(Quarantine quarantine, List<Long> userIds) {
        logger.info("Starting quarantine {} for {} users", quarantine.getCodeNumber(), userIds.size());
        
        // Validate quarantine
        if (quarantine.getCodeNumber() == null || quarantine.getCodeNumber().isBlank()) {
            throw new QuarantineException("Quarantine code number is required");
        }
        
        // Check if quarantine code already exists
        if (quarantineRepository.existsByCodeNumber(quarantine.getCodeNumber())) {
            throw new QuarantineException("Quarantine with code " + quarantine.getCodeNumber() + " already exists");
        }
        
        // Default values are already defined in the entity builder
        
        if (userIds != null && !userIds.isEmpty()) {
            List<User> users = userRepository.findAllById(userIds);
            if (users.size() != userIds.size()) {
                Set<Long> foundIds = users.stream().map(User::getId).collect(Collectors.toSet());
                List<Long> missingIds = userIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .toList();
                throw new ResourceNotFoundException("Users not found with ids " + missingIds);
            }
            quarantine.setUsers(users);
            logger.info("Assigned {} users to quarantine {}", users.size(), quarantine.getCodeNumber());
        }
        
        Quarantine saved = quarantineRepository.save(quarantine);
        logger.info("Quarantine {} started successfully", saved.getCodeNumber());
        return saved;
    }

    /**
     * Get quarantine by code number
     */
    @Transactional(readOnly = true)
    public Quarantine getQuarantineByCode(String codeNumber) {
        logger.debug("Retrieving quarantine with code: {}", codeNumber);
        return quarantineRepository.findByCodeNumber(codeNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Quarantine not found with code " + codeNumber));
    }

    /**
     * Get all quarantines
     */
    @Transactional(readOnly = true)
    public List<Quarantine> getAllQuarantines() {
        logger.debug("Retrieving all quarantines");
        return quarantineRepository.findAll();
    }

    /**
     * Check if a user is in quarantine
     */
    @Transactional(readOnly = true)
    public boolean isUserInQuarantine(Long userId) {
        logger.debug("Checking quarantine status for user: {}", userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return quarantineRepository.findAll().stream()
                .filter(Quarantine::isActive)
                .anyMatch(quarantine -> quarantine.getUsers().contains(user.get()));
    }

    /**
     * Get all active quarantines
     */
    @Transactional(readOnly = true)
    public List<Quarantine> getAllActiveQuarantines() {
        logger.debug("Retrieving all active quarantines");
        return quarantineRepository.findAll().stream()
                .filter(Quarantine::isActive)
                .toList();
    }

    /**
     * End a quarantine (if allowed)
     */
    @Transactional
    public void endQuarantine(String codeNumber) {
        logger.info("Attempting to end quarantine: {}", codeNumber);
        Quarantine quarantine = quarantineRepository.findByCodeNumber(codeNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Quarantine not found with code " + codeNumber));

        if (quarantine.isNonInterruptible()) {
            logger.warn("Attempted to end non-interruptible quarantine: {}", codeNumber);
            throw new QuarantineException("Quarantine " + codeNumber + " cannot be interrupted due to protocol " + quarantine.getProtocol());
        }

        quarantine.setActive(false);
        quarantineRepository.save(quarantine);
        logger.info("Quarantine {} ended successfully", codeNumber);
    }
}