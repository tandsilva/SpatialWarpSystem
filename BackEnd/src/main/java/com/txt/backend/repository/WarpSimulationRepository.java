package com.txt.backend.repository;

import com.txt.backend.model.WarpSimulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarpSimulationRepository extends JpaRepository<WarpSimulation, Long> {
    // Métodos personalizados podem ser adicionados aqui, se necessário
}