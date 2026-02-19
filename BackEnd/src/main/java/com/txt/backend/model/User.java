package com.txt.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.txt.backend.role.Role;
import com.txt.backend.role.ContaminationStatus;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique user identifier
    private String name; // User name
    private String password; // Password for authentication

    @Enumerated(EnumType.STRING)
    private Role role; // User role in the system (e.g., CAPTAIN, CREW)

    private boolean isActive; // Indicates whether the user is active
    private boolean isBlocked; // Indicates whether the user is blocked due to infection or parasite
    private String crew; // Identifies the crew the user belongs to

    @Enumerated(EnumType.STRING)
    private ContaminationStatus contaminationStatus; // User contamination status

    // Additional methods can be added as needed
}