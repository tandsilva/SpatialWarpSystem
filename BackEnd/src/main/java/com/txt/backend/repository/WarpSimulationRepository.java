package com.txt.backend.repository;

import com.txt.backend.model.WarpSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpSimulationRepository extends JpaRepository<WarpSimulation, Long> {
    // Custom methods can be added here, if needed
}