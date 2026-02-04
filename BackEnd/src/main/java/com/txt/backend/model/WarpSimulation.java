package com.txt.backend.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "warp_simulations")
public class WarpSimulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double initialX;
    private double initialY;
    private double initialZ;

    private double finalX;
    private double finalY;
    private double finalZ;

    private double bubbleVelocity;
    private double bubbleSize;
    private double energyRequired;

    private String warpPhase; // START, CRUISE, END
    private boolean obstaclesDetected;
    private double travelTime;

}