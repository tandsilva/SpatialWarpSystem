package com.txt.backend.repository;

import com.txt.backend.model.Quarantine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuarantineRepository extends JpaRepository<Quarantine, Long> {

    // Buscar todas as quarentenas ativas
    List<Quarantine> findByActiveTrue();

    // Buscar quarentenas por laboratório
    List<Quarantine> findByLabId(String labId);
    
    // Buscar quarentena por código
    Optional<Quarantine> findByCodeNumber(String codeNumber);
    
    // Verificar se existe quarentena com código
    boolean existsByCodeNumber(String codeNumber);
}
