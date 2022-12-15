package com.example.registration.onboarding.signup;

import com.example.registration.onboarding.appuser.*;
import com.example.registration.security.email.EmailSender;
import com.example.registration.security.phone.SmsSender;
import com.example.registration.onboarding.signup.token.ConfirmationToken;
import com.example.registration.onboarding.signup.token.ConfirmationTokenService;
import com.example.registration.onboarding.twilio.storage.GeneratedOtp;
import com.example.registration.onboarding.twilio.storage.GeneratedOtpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final UserFarmerService userFarmerService;
    private final  UserFarmerRepository userFarmerRepository;
    private final EmailValidator emailValidator;
    private final PhoneValidator phoneValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final GeneratedOtpService generatedOtpService;
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = appUserService.signUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getConfirmPassword(),
                        Roles.USER

                )
        );

        emailSender.send(
                request.getEmail(),
                buildEmail(request.getUsername(), token));

        return "Thank you for registering. \nPlease check your email for a verification code to activate your account.";
    }
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email is already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return  "Email confirmed successfully.\nProceed to login!.";
    }

    public String registerPhone(PhoneRequest request) {
        boolean isValidPhone = phoneValidator.
                test(request.getPhone());

        if (!isValidPhone) {
            throw new IllegalStateException("phone number not valid");
        }

        String otp = userFarmerService.signUpUser(
                new UserFarmer(request.getUsername(),
                        request.getPhone(),
                        request.getPassword(),
                        Roles.USER

                )
        );
        smsSender.sendSms(request.getPhone(), otp);
        System.out.println(otp);
        return "Thank you for registering. \nA verification code to activate your account has been sent to you phone number.";
    }
    public String resendOTPPhone(ResendRequest request) {
        boolean isValidated = false;
        if (isValidated == (userFarmerRepository.findByPhone(request.getPhone()).getEnabled())) {
            String otp = userFarmerService.generateOTP();
            UserFarmer userFarmer= userFarmerRepository.findByPhone(request.getPhone());
            GeneratedOtp generatedOtp = new GeneratedOtp(otp,LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(2),
                    userFarmer );
            generatedOtpService.saveGeneratedOtp(generatedOtp);
            generatedOtpService.saveGeneratedOtp(generatedOtp);
            smsSender.sendSms(request.getPhone(), otp);
            return "A verification code to activate your account has been resent to you phone number.";
        }else return "Verification code is not expired yet";
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
        userFarmerService.enableUserFarmer(generatedOtp.getUserFarmer().getPhone());
        return  "Phone number confirmed successfully.\nProceed to login!.";
    }

    private String buildEmail(String name, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hello " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Glad to have you on board. \nPlease use the verification code below to confirm your email address.</p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"></blockquote><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#040404\"> \"" + token + "\" </p>\n This code will expire in 2 minutes. <p>If you didn't request this verification code, you can safely ignore this email. Someone else might have typed your email address by mistake.</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
