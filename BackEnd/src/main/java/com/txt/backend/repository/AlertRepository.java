package com.txt.backend.repository;

import com.txt.backend.model.AlertHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<AlertHistory, Long> {
    // Custom query to find critical alerts specifically if needed later
    List<AlertHistory> findBySeverity(String severity);
}
