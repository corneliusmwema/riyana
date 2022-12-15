package com.example.registration.security.phone;

import com.example.registration.onboarding.twilio.configurations.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsService implements SmsSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(SmsService.class);
    private final TwilioConfiguration twilioConfiguration;

    @Override
    public void sendSms(String recipient, String sms) {
        PhoneNumber to = new PhoneNumber(recipient);
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
        MessageCreator creator = Message.creator(to, from, "Use verification code "+sms+" for Shamba App authentication.");
        creator.create();
        LOGGER.info("Send sms {}",sms);
    }
}
