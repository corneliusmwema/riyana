package com.example.registration.onboarding.forgotpassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class NewPasswordRequest {
    private String newPassword;
    public String setNewPassword() {
        return newPassword;
    }

}
