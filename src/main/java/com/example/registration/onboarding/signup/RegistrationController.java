package com.example.registration.onboarding.signup;

import com.example.registration.onboarding.appuser.AppUser;
import com.example.registration.onboarding.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "signup")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserRepository appUserRepository;

    @GetMapping("usersByEmail")
    public List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }

    @GetMapping("UserById")
    public String getUserById(@RequestParam Long id) {
        appUserRepository.findById(id);
        return "User with ID " + id + " does not exist.";
    }
    @GetMapping("usersByPhone")
    public List<AppUser> getUsersPhone(){ return appUserRepository.findAll(); }

    @GetMapping("UserByIdPhone")
    public String getUserByIdPhone(@RequestParam Long id) {
        appUserRepository.findById(id);
        return "User with ID " + id + " does not exist.";
    }

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
