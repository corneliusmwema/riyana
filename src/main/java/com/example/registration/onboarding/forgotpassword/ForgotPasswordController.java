package com.example.registration.onboarding.forgotpassword;

import com.example.registration.onboarding.signup.PhoneValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("forgotpassword")
@AllArgsConstructor
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;
    private PhoneValidator phoneValidator;

    @PostMapping(path = "request")
    public String resetAccountPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws Exception{
        boolean isValid = phoneValidator.test(forgotPasswordRequest.getPhone());
        if(!isValid){throw new Exception("Enter genuine/valid phone number");}
        return forgotPasswordService.checkPhoneValidity(forgotPasswordRequest);
    }
    @PostMapping(path = "validate")
    public String confirmPhone(@RequestParam("OTP") String otp) {
        return forgotPasswordService.confirmOtp(otp);
    }
    @PostMapping(path = "changepassword")
    public String changePassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        return forgotPasswordService.newPassword(newPasswordRequest);
    }
}
