package com.txt.BackEnd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;
import com.txt.BackEnd.role.EmergencyProtocol;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entity representing a quarantine protocol for contaminated crew members.
 * Implements emergency procedures with specific time-based lockdown protocols.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "quarantines", indexes = {
        @Index(name = "idx_lab_id", columnList = "labId"),
        @Index(name = "idx_active", columnList = "active")
})
public class Quarantine {
    
    @Id
    @Column(nullable = false, unique = true, length = 50)
    private String codeNumber; // Unique quarantine code
    
    @Column(nullable = false, length = 50)
    private String labId; // Laboratory identification

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmergencyProtocol protocol; // Emergency protocol applied

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "quarantine_users",
        joinColumns = @JoinColumn(name = "quarantine_code", referencedColumnName = "codeNumber"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users; // List of quarantined users

    @Column(nullable = false, length = 500)
    private String reason; // Reason for quarantine
    
    @Column(nullable = false)
    private boolean active = true; // Quarantine status
    
    @Column(nullable = false)
    private boolean nonInterruptible = true; // Whether quarantine can be interrupted

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Quarantine start time

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Last modification time

    @Column
    private LocalDateTime estimatedEndTime; // Calculated end time based on protocol

    /**
     * Check if quarantine can be terminated
     */
    public boolean canEndQuarantine() {
        return !nonInterruptible;
    }

    /**
     * Calculate and set estimated end time based on protocol
     */
    @PrePersist
    @PreUpdate
    public void calculateEstimatedEndTime() {
        if (protocol != null && createdAt != null) {
            this.estimatedEndTime = createdAt.plusHours(protocol.getLockdownHours());
        }
    }

    /**
     * Check if quarantine period has expired
     */
    public boolean hasExpired() {
        if (estimatedEndTime == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(estimatedEndTime);
    }

    /**
     * Get remaining hours in quarantine
     */
    public long getRemainingHours() {
        if (estimatedEndTime == null) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        return java.time.Duration.between(now, estimatedEndTime).toHours();
    }
}