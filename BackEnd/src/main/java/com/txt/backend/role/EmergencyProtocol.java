package com.txt.backend.role;

public enum EmergencyProtocol {
    PROTOCOL_1(48, "Bloqueio de 48 horas - Contaminação leve"),
    PROTOCOL_2(72, "Bloqueio de 72 horas - Contaminação moderada"),
    PROTOCOL_3(168, "Bloqueio de 7 dias - Contaminação grave"),
    PROTOCOL_4(336, "Bloqueio de 14 dias - Contaminação crítica"),
    PROTOCOL_5(720, "Bloqueio de 30 dias - Quarentena total");

    private final int lockdownHours;
    private final String description;

    EmergencyProtocol(int lockdownHours, String description) {
        this.lockdownHours = lockdownHours;
        this.description = description;
    }

    public int getLockdownHours() {
        return lockdownHours;
    }

    public String getDescription() {
        return description;
    }
}
