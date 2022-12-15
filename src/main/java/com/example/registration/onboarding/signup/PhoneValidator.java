package com.example.registration.onboarding.signup;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PhoneValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        Pattern pattern = Pattern.compile("(?:\\+254)(7(?:(?:[9][0-9])|(?:[8][0-9])|(?:[7][0-9])|(?:[6][0-9])|(?:[5][0-9])|(?:[4][0-8])|(?:[3][0-9])|(?:[2][0-9])|(?:[1][0-9])|([0][0-9]))[0-9]{6})");//(0/91): number starts with (0/91)
        Matcher match = pattern.matcher(s);
        return (match.find() && match.group().equals(s));
    }
}
