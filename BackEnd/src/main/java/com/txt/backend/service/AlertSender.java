package com.txt.backend.service;

import com.txt.backend.dto.SystemAlert;

public interface AlertSender {
    void sendCriticalAlert(SystemAlert alert);
}
