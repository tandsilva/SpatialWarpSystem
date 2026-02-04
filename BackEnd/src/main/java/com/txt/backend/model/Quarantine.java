package com.txt.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.txt.backend.role.EmergencyProtocol;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a quarantine protocol for contaminated crew members.
 * Implements emergency procedures with specific time-based lockdown protocols.
 * 
 * @author Space Station Backend Team
 * @version 1.0
 * @since 2026-02-03
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "quarantines",
    indexes = {
        @Index(name = "idx_code_number", columnList = "code_number", unique = true),
        @Index(name = "idx_lab_id", columnList = "lab_id"),
        @Index(name = "idx_active", columnList = "active"),
        @Index(name = "idx_created_at", columnList = "created_at")
    }
)
public class Quarantine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Code number is required")
    @Size(min = 3, max = 50, message = "Code number must be between 3 and 50 characters")
    @Column(name = "code_number", nullable = false, unique = true, length = 50)
    private String codeNumber;

    @NotBlank(message = "Lab ID is required")
    @Size(max = 50, message = "Lab ID must not exceed 50 characters")
    @Column(name = "lab_id", nullable = false, length = 50)
    private String labId;

    @NotNull(message = "Emergency protocol is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmergencyProtocol protocol;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "quarantine_users",
        joinColumns = @JoinColumn(name = "quarantine_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    @Builder.Default
    private List<User> users = new ArrayList<>();

    @NotBlank(message = "Reason is required")
    @Size(max = 500, message = "Reason must not exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String reason;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @Column(name = "non_interruptible", nullable = false)
    @Builder.Default
    private boolean nonInterruptible = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "estimated_end_time")
    private LocalDateTime estimatedEndTime;

    @Version
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Long version = 0L;

    /**
     * Verifica se a quarentena pode ser encerrada.
     * 
     * @return true se a quarentena puder ser interrompida
     */
    public boolean canEndQuarantine() {
        return !nonInterruptible;
    }

    /**
     * Calcula e define o tempo estimado de término baseado no protocolo.
     * Executado automaticamente antes de persistir ou atualizar.
     */
    @PrePersist
    @PreUpdate
    public void calculateEstimatedEndTime() {
        if (protocol != null && createdAt != null) {
            this.estimatedEndTime = createdAt.plusHours(protocol.getLockdownHours());
        }
    }

    /**
     * Verifica se o período de quarentena expirou.
     * 
     * @return true se o tempo estimado já passou
     */
    public boolean hasExpired() {
        if (estimatedEndTime == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(estimatedEndTime);
    }

    /**
     * Obtém as horas restantes de quarentena.
     * 
     * @return número de horas restantes, ou 0 se já expirou
     */
    public long getRemainingHours() {
        if (estimatedEndTime == null || hasExpired()) {
            return 0;
        }
        return java.time.Duration.between(LocalDateTime.now(), estimatedEndTime).toHours();
    }

    /**
     * Adiciona um usuário à lista de quarentena.
     * 
     * @param user usuário a ser adicionado
     */
    public void addUser(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    /**
     * Remove um usuário da lista de quarentena.
     * 
     * @param user usuário a ser removido
     */
    public void removeUser(User user) {
        if (users != null) {
            users.remove(user);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quarantine)) return false;
        Quarantine that = (Quarantine) o;
        return codeNumber != null && codeNumber.equals(that.codeNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Quarantine{" +
                "id=" + id +
                ", codeNumber='" + codeNumber + '\'' +
                ", labId='" + labId + '\'' +
                ", protocol=" + protocol +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}