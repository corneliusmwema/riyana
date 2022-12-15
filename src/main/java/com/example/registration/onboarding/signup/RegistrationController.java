package com.example.registration.onboarding.signup;

import com.example.registration.onboarding.appuser.AppUser;
import com.example.registration.onboarding.appuser.AppUserRepository;
import com.example.registration.onboarding.appuser.UserFarmer;
import com.example.registration.onboarding.appuser.UserFarmerRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "signup")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AppUserRepository appUserRepository;
    private final UserFarmerRepository userFarmerRepository;


    @GetMapping("usersByEmail")
    public List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }

    @GetMapping("UserById")
    public String getUserById(@RequestParam Long id) {
        appUserRepository.findById(id);
        return "User with ID " + id + " does not exist.";
    }

    @PostMapping("email")
    public String register(@RequestBody RegistrationRequest request) {

        return registrationService.register(request);
    }

    @PostMapping(path = "email/confirm")
    public String confirm(@RequestParam("OTP") String token) {
        return registrationService.confirmToken(token);
    }

    @GetMapping("usersByPhone")
    public List<UserFarmer> getUsersPhone(){ return userFarmerRepository.findAll(); }

    @GetMapping("UserByIdPhone")
    public String getUserByIdPhone(@RequestParam Long id) {
        userFarmerRepository.findById(id);
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
