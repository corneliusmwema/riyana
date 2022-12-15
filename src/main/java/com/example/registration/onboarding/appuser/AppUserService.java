package com.example.registration.onboarding.appuser;

import com.example.registration.onboarding.signup.token.ConfirmationToken;
import com.example.registration.onboarding.signup.token.ConfirmationTokenService;
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
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email);
        if(user==null){
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }



    public String signUpUser(AppUser appUser) throws EntityExistsException {
        AppUser userExists = appUserRepository.findByEmail(appUser.getEmail());

        if (userExists!=null) { throw new IllegalStateException("email already taken"); }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        String token = generateOTP();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(2),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    public String generateOTP(){
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

//    public void changeUserPassword(String password, String phone){
//        appUserRepository.changeUserPassword(password, phone);
//    }

}
