package com.riyana.app.security.phone;

import org.springframework.scheduling.annotation.Async;

public interface SmsSender {
    @Async
    void sendSms(String recipient, String sms);
}
