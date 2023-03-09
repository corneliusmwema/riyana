package com.riyana.app.security.twilio.storage;

import com.riyana.app.onboarding.Entities.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GeneratedOtp {

    @SequenceGenerator(
            name = "generated_otp_sequence",
            sequenceName = "generated_otp_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "generated_otp_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private AppUser appUser;

    public GeneratedOtp(String otp, LocalDateTime createdAt, LocalDateTime expiresAt, AppUser appUser) {
        this.otp = otp;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
