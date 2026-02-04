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
    private Long id; // Identificador único do usuário
    private String name; // Nome do usuário
    private String password; // Senha para autenticação

    @Enumerated(EnumType.STRING)
    private Role role; // Papel do usuário no sistema (ex.: CAPTAIN, CREW)

    private boolean isActive; // Indica se o usuário está ativo
    private boolean isBlocked; // Indica se o usuário está bloqueado devido a infecção ou parasita
    private String crew; // Identifica a tripulação à qual o usuário pertence

    @Enumerated(EnumType.STRING)
    private ContaminationStatus contaminationStatus; // Status de contaminação do usuário

    // Métodos adicionais podem ser adicionados conforme necessário
}