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

    // New fields based on aldrin.txt
    private double currentSpeed; // Current ship speed
    private double maxSpeed; // Maximum allowed speed (speed of light)
    private boolean warpDriveActive; // Warp drive status
    private double warpEnergyRequired; // Energy required for warp
    private boolean atmosphereGeneratorActive; // Atmosphere generator status
    private double co2Level; // CO2 level
    private boolean autoNavigationActive; // Automatic navigation active
    private String emergencyProtocol; // Active emergency protocol
    private boolean aiAssistanceActive; // AI assistance active
    private int droidsAvailable; // Number of available droids

}