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
public class ForgotPasswordRequest {
    private String phone;
  public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
