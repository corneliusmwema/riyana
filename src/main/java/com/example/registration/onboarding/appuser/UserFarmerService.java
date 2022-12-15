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
public class UserFarmerService  implements UserDetailsService{
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final UserFarmerRepository userFarmerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GeneratedOtpService generatedOtpService;



    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        UserFarmer user = userFarmerRepository.findByPhone(phone);
        if(user==null){
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, phone));
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }



    public String signUpUser(UserFarmer userFarmer) throws EntityExistsException {
        UserFarmer userExists = userFarmerRepository.findByPhone(userFarmer.getPhone());

        if (userExists!=null) { throw new IllegalStateException("phone number already taken"); }

        String encodedPassword = bCryptPasswordEncoder.encode(userFarmer.getPassword());

        userFarmer.setPassword(encodedPassword);

        userFarmerRepository.save(userFarmer);

        String otp = generateOTP();

        GeneratedOtp generatedOtp = new GeneratedOtp(
                otp,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                userFarmer
        );

        generatedOtpService.saveGeneratedOtp(generatedOtp);

//        TODO: SEND SMS

        return otp;
    }

    public String generateOTP(){ return new DecimalFormat("000000").format(new Random().nextInt(999999));}

    public int enableUserFarmer(String phone) {
        return userFarmerRepository.enableUserFarmer(phone);
    }

    public void changeUserPassword(String phone){ userFarmerRepository.changeUserPassword(phone); }

}
