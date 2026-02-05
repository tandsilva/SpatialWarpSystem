package com.txt.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alert_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String systemSource; // e.g., LIFE_SUPPORT
    private String severity;     // e.g., CRITICAL
    private String message;      // e.g., Oxygen level hypoxic
    private LocalDateTime timestamp;
    private String automatedActionTaken; // e.g., Droid dispatched
}
