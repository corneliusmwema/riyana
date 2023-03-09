package com.riyana.app.onboarding.Controllers;

import com.riyana.app.onboarding.Entities.AppUser;
import com.riyana.app.onboarding.Models.PhoneRequest;
import com.riyana.app.onboarding.Models.ResendRequest;
import com.riyana.app.onboarding.Repositories.AppUserRepository;
import com.riyana.app.onboarding.Services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "signup")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserRepository appUserRepository;

    @GetMapping("UserById")
    public String getUserById(@RequestParam Long id) {
        appUserRepository.findById(id);
        return "User with id " + id + " does not exist.";
    }
    @GetMapping("usersByPhone")
    public List<AppUser> getUsersPhone(){ return appUserRepository.findAll(); }

    @PostMapping("phone")
    public String registerPhone(@RequestBody PhoneRequest request) {

        return registrationService.registerPhone(request);
    }

    @PostMapping(path = "phone/resendOTP")
    public String resend(@RequestBody ResendRequest request){
        return registrationService.resendOTPPhone(request);
    }

    @PostMapping(path = "phone/confirm")
    public String confirmPhone(@RequestParam("OTP") String otp){
        return registrationService.confirmOtp(otp);
    }
}
