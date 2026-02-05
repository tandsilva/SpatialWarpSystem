package com.txt.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryData implements Serializable {
    private String sensorId;
    private String type; // ex: temperatura, radiacao, velocidade
    private double value;
    private String unit;
    private String timestamp;
}
