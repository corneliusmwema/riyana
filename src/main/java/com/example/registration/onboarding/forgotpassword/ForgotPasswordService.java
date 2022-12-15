package com.example.registration.onboarding.forgotpassword;

import com.example.registration.onboarding.appuser.UserFarmer;
import com.example.registration.onboarding.appuser.UserFarmerRepository;
import com.example.registration.onboarding.appuser.UserFarmerService;
import com.example.registration.security.phone.SmsSender;
import com.example.registration.onboarding.twilio.storage.GeneratedOtp;
import com.example.registration.onboarding.twilio.storage.GeneratedOtpService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

@AllArgsConstructor
@Service
public class ForgotPasswordService {
    private final UserFarmerRepository userFarmerRepository;
    private final UserFarmerService userFarmerService;
    private final SmsSender smsSender;
    @Autowired
    ForgotPasswordRequest forgotPasswordRequest;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeneratedOtpService generatedOtpService;
    @Transactional
    public String checkPhoneValidity(ForgotPasswordRequest forgotPasswordRequest)
            throws IllegalStateException{
        UserFarmer userFarmer = userFarmerRepository.findByPhone(forgotPasswordRequest.getPhone());
        if(userFarmer ==null){
            throw new IllegalStateException("User does not exist!");
        }

        String otp = generateOTP();
        GeneratedOtp generatedOtp = new GeneratedOtp(
                otp,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(1),
                userFarmer
        );
        generatedOtpService.saveGeneratedOtp(generatedOtp);
        smsSender.sendSms(forgotPasswordRequest.getPhone(), otp);
//        TODO: SEND SMS
        return "An otp has been sent to your phone number.";
        }
    public String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
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

        return  "OTP validated successfully. Proceed to enter new password.";

        }
      @Transactional
    public String newPassword(NewPasswordRequest newPasswordRequest){
              userFarmerService.changeUserPassword(bCryptPasswordEncoder.encode(newPasswordRequest.setNewPassword()));
              return  "password changed successfully.";    }
}
