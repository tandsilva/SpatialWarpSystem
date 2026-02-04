package com.txt.backend.service;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    // Method to detect system failures
    public String detectFailures(double oxygenLevel, double energyLevel, boolean hullIntegrity) {
        StringBuilder report = new StringBuilder();

        if (oxygenLevel < 19.5) {
            report.append("FAILURE: Oxygen level critical. ");
        }
        if (energyLevel < 10) {
            report.append("FAILURE: Energy level critical. ");
        }
        if (!hullIntegrity) {
            report.append("FAILURE: Hull integrity compromised. ");
        }

        if (report.length() == 0) {
            return "SYSTEM_NORMAL: All systems nominal.";
        } else {
            return report.toString().trim();
        }
    }

    // Method to coordinate droid repairs
    public String coordinateDroidRepair(String failureType, int availableDroids) {
        if (availableDroids == 0) {
            return "ERROR: No droids available for repair.";
        }

        switch (failureType.toLowerCase()) {
            case "oxygen":
                return "DISPATCH: Droid sent to atmosphere generator.";
            case "energy":
                return "DISPATCH: Droid sent to energy grid.";
            case "hull":
                return "DISPATCH: Droid sent to hull breach.";
            default:
                return "UNKNOWN: Manual intervention required.";
        }
    }

    // Method to check crew vitals (simulation)
    public boolean checkCrewVitals(int crewSize) {
        // Simulation: assumes everyone is fine if crewSize > 0
        return crewSize > 0;
    }

}