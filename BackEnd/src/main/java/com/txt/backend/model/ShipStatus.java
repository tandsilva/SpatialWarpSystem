package com.txt.backend.model;

import lombok.Data;

@Data
public class ShipStatus {

    private String crewStatus;
    private String hullIntegrity;
    private double internalTemperature;
    private double internalPressure;
    private double gravityLevel;
    private double engineEnergy;
    private double energyConsumption;
    private double oxygenLevel;
    private String postWarpLocation;

    // Novos campos baseados no arquivo aldrin.txt
    private double currentSpeed; // Velocidade atual da nave
    private double maxSpeed; // Velocidade máxima permitida (velocidade da luz)
    private boolean warpDriveActive; // Status da dobra espacial
    private double warpEnergyRequired; // Energia necessária para dobra
    private boolean atmosphereGeneratorActive; // Status do gerador de atmosfera
    private double co2Level; // Nível de CO2
    private boolean autoNavigationActive; // Navegação automática ativa
    private String emergencyProtocol; // Protocolo de emergência ativo
    private boolean aiAssistanceActive; // Assistência de IA ativa
    private int droidsAvailable; // Número de droids disponíveis

}