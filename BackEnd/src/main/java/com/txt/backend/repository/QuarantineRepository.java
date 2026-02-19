package com.txt.backend.repository;

import com.txt.backend.model.Quarantine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuarantineRepository extends JpaRepository<Quarantine, Long> {

    // Find all active quarantines
    List<Quarantine> findByActiveTrue();

    // Find quarantines by lab
    List<Quarantine> findByLabId(String labId);
    
    // Find quarantine by code
    Optional<Quarantine> findByCodeNumber(String codeNumber);
    
    // Check whether a quarantine exists with the given code
    boolean existsByCodeNumber(String codeNumber);
}
