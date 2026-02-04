package com.txt.backend.config;

/**
 * Application constants for spaceship systems
 */
public final class Constants {
    
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Physics constants
    public static final double SPEED_OF_LIGHT = 299792458.0; // m/s
    public static final double IDEAL_OXYGEN_LEVEL = 20.5; // percentage
    public static final double CRITICAL_OXYGEN_LEVEL = 19.5; // percentage
    public static final double MINIMUM_SAFE_OXYGEN = 19.0; // percentage

    // Energy thresholds
    public static final double CRITICAL_ENERGY_LEVEL = 10.0; // percentage
    public static final double LOW_ENERGY_LEVEL = 30.0; // percentage
    public static final double BASE_ENERGY_CONSUMPTION = 100.0; // kW

    // Warp drive constants
    public static final double WARP_ACTIVATION_THRESHOLD = 0.1; // 10% of light speed
    public static final double LIGHT_SPEED_WARNING_THRESHOLD = 0.9; // 90% of light speed
    public static final double ENERGY_MULTIPLIER = 1e12; // For warp energy calculations

    // Atmosphere constants
    public static final double NORMAL_CREW_OXYGEN_CONSUMPTION = 0.5; // L/min per person
    public static final double ATMOSPHERE_GENERATOR_MIN_EFFICIENCY = 80.0; // percentage

    // System priorities
    public static final String[] CRITICAL_SYSTEMS = {
        "Life Support",
        "Navigation",
        "Communication"
    };

    // Validation constants
    public static final int MIN_CODE_LENGTH = 3;
    public static final int MAX_CODE_LENGTH = 50;
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 255;
    public static final int MIN_REASON_LENGTH = 10;
    public static final int MAX_REASON_LENGTH = 500;

    // Error messages
    public static final String USER_NOT_FOUND = "User not found with id: %d";
    public static final String QUARANTINE_NOT_FOUND = "Quarantine not found with code: %s";
    public static final String INVALID_OPERATION = "Invalid operation: %s";
    public static final String QUARANTINE_CANNOT_BE_INTERRUPTED = "Quarantine %s cannot be interrupted due to protocol %s";
}
