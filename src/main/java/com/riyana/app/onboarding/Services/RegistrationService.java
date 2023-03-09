package com.riyana.app.onboarding.Services;

import com.riyana.app.onboarding.Entities.AppUser;
import com.riyana.app.onboarding.Models.PhoneRequest;
import com.riyana.app.onboarding.Models.ResendRequest;
import com.riyana.app.onboarding.Repositories.AppUserRepository;
import com.riyana.app.onboarding.Services.AppUserService;
import com.riyana.app.security.validator.PhoneValidator;
import com.riyana.app.security.validator.Roles;
import com.riyana.app.security.twilio.storage.GeneratedOtp;
import com.riyana.app.security.twilio.storage.GeneratedOtpService;
import com.riyana.app.security.phone.SmsSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final PhoneValidator phoneValidator;
    private final GeneratedOtpService generatedOtpService;
    private final SmsSender smsSender;

    public String registerPhone(PhoneRequest request) {
        boolean isValidPhone = phoneValidator.
                test(request.getPhone());

        if (!isValidPhone) {
            throw new IllegalStateException("phone number not valid");
        }
        String otp = appUserService.signUpUser(new AppUser(request.getNickname(),
                        request.getPhone(), request.getPassword(), Roles.USER )
        );
        smsSender.sendSms(request.getPhone(), otp);
        System.out.println(otp);
        return "Thank you for registering,a verification code to validate your registration has been sent to you phone number.";
    }
    public String resendOTPPhone(ResendRequest request) {
        boolean isValidated = false;
        if (isValidated == (appUserRepository.findByPhone(request.getPhone()).getEnabled())) {
            String otp = appUserService.generateOTP();
            AppUser appUser = appUserRepository.findByPhone(request.getPhone());
            GeneratedOtp generatedOtp = new GeneratedOtp(otp,LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(2),
                    appUser);
            generatedOtpService.saveGeneratedOtp(generatedOtp);
            generatedOtpService.saveGeneratedOtp(generatedOtp);
            smsSender.sendSms(request.getPhone(), otp);
            return "A verification code to activate your account has been resent to you phone number.";
        }else
            return "Verification code is not expired yet";
    }

    @Transactional
    public String confirmOtp(String otp) {
        GeneratedOtp generatedOtp = generatedOtpService.getOtp(otp)
                .orElseThrow(() ->
                        new IllegalStateException("otp not found"));

        if (generatedOtp.getConfirmedAt() != null) {
            throw new IllegalStateException("Phone number is already confirmed");
        }
        LocalDateTime expiredAt = generatedOtp.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("otp expired");
        }
        generatedOtpService.setConfirmedAt(otp);
        appUserService.enableUserFarmer(generatedOtp.getAppUser().getPhone());
        return  "Phone number confirmed successfully. Proceed to login.";
    }
}
