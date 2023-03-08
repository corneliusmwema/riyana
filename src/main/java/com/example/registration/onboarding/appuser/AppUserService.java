package com.example.registration.onboarding.appuser;

import com.example.registration.onboarding.twilio.storage.GeneratedOtp;
import com.example.registration.onboarding.twilio.storage.GeneratedOtpService;
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
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeneratedOtpService generatedOtpService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByPhone(phone);
        if(user==null){
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, phone));
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }



    public String signUpUser(AppUser appUser) throws EntityExistsException {
        AppUser userExists = appUserRepository.findByPhone(appUser.getPhone());

        if (userExists!=null) { throw new IllegalStateException("phone number already taken"); }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String otp = generateOTP();

        GeneratedOtp generatedOtp = new GeneratedOtp(
                otp,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                appUser
        );

        generatedOtpService.saveGeneratedOtp(generatedOtp);

//        TODO: SEND SMS

        return otp;
    }

    public String generateOTP(){ return new DecimalFormat("000000").format(new Random().nextInt(999999));}

    public int enableUserFarmer(String phone) {
        return appUserRepository.enableUserFarmer(phone);
    }

}
