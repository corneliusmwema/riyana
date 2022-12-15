//package com.example.registration.onboarding.forgotpassword;
//
//import com.example.registration.onboarding.appuser.UserFarmer;
//import com.example.registration.onboarding.appuser.UserFarmerRepository;
//import com.example.registration.onboarding.appuser.UserFarmerService;
//import com.example.registration.onboarding.twilio.storage.GeneratedOtp;
//import com.example.registration.onboarding.twilio.storage.GeneratedOtpRepository;
//import com.example.registration.security.jwts.JwtFilter;
//import com.example.registration.security.phone.SmsSender;
//import com.frex.JWT.JwtTokenFilter;
//import com.frex.Twilio.Config.TwilioConfiguration;
//import com.frex.model.ConfirmationToken;
//import com.frex.model.User;
//import com.frex.reposirory.ConfirmationTokenRepository;
//import com.frex.reposirory.UserRepository;
//import com.frex.service.EmailService;
//import com.frex.service.UserService;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.rest.api.v2010.account.MessageCreator;
//import com.twilio.type.PhoneNumber;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//
//@RestController
//@CrossOrigin
//public class ResetController {
//
//    @Autowired
//    private SmsSender smsSender;
//    @Autowired
//    private UserFarmerRepository userRepository;
//    @Autowired
//    JwtFilter jwtFilter;    @Autowired
//    private UserFarmerService userService;
//
//    @Autowired
//    private GeneratedOtpRepository generatedOtpRepository;
//
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
//
//    @RequestMapping(value = "/forgot-password-by-phone", method = RequestMethod.POST)
//    public ResponseEntity<?> forgotUserPasswordByPhone(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
//        UserFarmer exists = userRepository.findByPhone(forgotPasswordRequest.getPhone());
//        if (exists != null) {
//            // Create token
//            String otp = userService.generateOTP();
//            GeneratedOtp generatedOtp = new GeneratedOtp(otp,LocalDateTime.now(),
//                    LocalDateTime.now().plusMinutes(2), exists);
//
//            // Save it
//            generatedOtpService.saveGeneratedOtp(generatedOtp);
//        smsSender.sendSms(forgotPasswordRequest.getPhone(), otp);
//
//            return new ResponseEntity<>("ok", HttpStatus.OK);
//
//
//        } else {
//            return new ResponseEntity<>("User was not found", HttpStatus.OK);
//
//        }
//    }
//
//    @RequestMapping(value = "/confirm-reset", method = {RequestMethod.POST})
//    public ResponseEntity<?> validateResetToken(@RequestParam("token") String confirmationToken) {
//        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
//
//        if (token != null) {
//            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
//            user.setEnabled(true);
//            userRepository.save(user);
//            String message = "The token is confirmed";
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else {
//            String message = "The link is invalid or broken!";
//            //modelAndView.addObject("message", "The link is invalid or broken!");
//            //modelAndView.setViewName("error");
//            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//        }
//    }
//    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
//    public String resetPassword(@Valid ResetRequest resetRequest) {
//       ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(resetRequest.getToken());
//
//        if (token != null) {
//            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
//
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(resetRequest.getPassword());
//        String encodedCpassword = passwordEncoder.encode(resetRequest.getConfirmPassword());
//        user.setPassword(encodedPassword);
//        resetRequest.setConfirmPassword(encodedCpassword);
//        userRepository.save(user);
//            return "Updated successfully";
//        } else {
//            return "update was not successfull";
//        }
//
//    }
//}