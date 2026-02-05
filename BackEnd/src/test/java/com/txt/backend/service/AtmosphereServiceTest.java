//package com.txt.backend.service;
//
//import com.txt.backend.dto.SystemAlert;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
///**
// * Unit Tests for AtmosphereService.
// *
// * Strategy: "Mocking" external dependencies.
// * Instead of needing a real RabbitMQ server running (which failed due to network blocks),
// * we use @Mock to create a "Fake RabbitMQ".
// *
// * This allows us to verify if the logic *would* send a message, without actually sending it.
// * Ideally, Unit Tests should never depend on external infrastructure (Database, Network, Queue).
// */
//@ExtendWith(MockitoExtension.class)
//class AtmosphereServiceTest {
//
//    @Mock
//    private AlertSender alertSender;
//
//    @InjectMocks
//    private AtmosphereService atmosphereService;
//
//    @Test
//    @DisplayName("SCENARIO 1: CRITICAL - Should trigger alert and CALL AlertSender")
//    void shouldTriggerCriticalAlert() {
//        // Arrange (Given)
//        double hypoxicOxygenLevel = 18.0;
//
//        // Act (When)
//        String result = atmosphereService.monitorOxygenLevel(hypoxicOxygenLevel, 0.04);
//
//        // Assert (Then)
//        assertEquals("CRITICAL: Oxygen level too low! Activating emergency protocols.", result);
//
//        // VERIFICATION: Did the code try to send a message?
//        verify(alertSender, times(1)).sendCriticalAlert(any(SystemAlert.class));
//    }
//
//    @Test
//    @DisplayName("SCENARIO 2: WARNING - Should return warning and SILENCE RabbitMQ")
//    void shouldReturnWarning() {
//        // Arrange
//        double warningOxygenLevel = 20.0; // Between 19.5 and 20.5
//
//        // Act
//        String result = atmosphereService.monitorOxygenLevel(warningOxygenLevel, 0.04);
//
//        // Assert
//        assertEquals("WARNING: Oxygen level below ideal. Adjusting atmosphere generator.", result);
//
//        // VERIFICATION: Ensure NO message was sent.
//        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(SystemAlert.class));
//    }
//
//    @Test
//    @DisplayName("SCENARIO 3: NORMAL - Should return normal and SILENCE RabbitMQ")
//    void shouldReturnNormal() {
//        // Arrange
//        double idealOxygenLevel = 21.0;
//
//        // Act
//        String result = atmosphereService.monitorOxygenLevel(idealOxygenLevel, 0.04);
//
//        // Assert
//        assertEquals("NORMAL: Oxygen level stable.", result);
//
//        // VERIFICATION: Ensure NO message was sent.
//        verify(rabbitTemplate, never()).convertAndSend(anyString(), anyString(), any(SystemAlert.class));
//    }
//}
