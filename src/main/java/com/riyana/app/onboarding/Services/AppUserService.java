package com.riyana.app.onboarding.Services;

import com.riyana.app.onboarding.Entities.AppUser;
import com.riyana.app.onboarding.Repositories.AppUserRepository;
import com.riyana.app.security.twilio.storage.GeneratedOtp;
import com.riyana.app.security.twilio.storage.GeneratedOtpService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
    private final static String USER_NOT_FOUND_MSG = "user with the provided phone number %s is not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeneratedOtpService generatedOtpService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByPhone(phone);
        if(user==null){
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, phone));
        }
        return new User(user.getNickname(), user.getPassword(), user.getAuthorities());
    }

    public String signUpUser(AppUser appUser) throws EntityExistsException {
        AppUser userExists = appUserRepository.findByPhone(appUser.getPhone());
        if (userExists!=null) { throw new IllegalStateException("phone number already taken"); }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        String otp = generateOTP();
        GeneratedOtp generatedOtp = new GeneratedOtp(otp,LocalDateTime.now(),LocalDateTime.now().plusMinutes(2),appUser);
        generatedOtpService.saveGeneratedOtp(generatedOtp);
        return otp;
    }
    public String generateOTP(){ return new DecimalFormat("000000").format(new Random().nextInt(999999));}
    public int enableUserFarmer(String phone) { return appUserRepository.enableUserFarmer(phone); }
}
