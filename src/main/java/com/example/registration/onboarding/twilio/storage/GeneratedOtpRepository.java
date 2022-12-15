package com.example.registration.onboarding.twilio.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface GeneratedOtpRepository
        extends JpaRepository<GeneratedOtp, Long> {

    Optional<GeneratedOtp> findByOtp(String otp);
    @Transactional
    @Modifying
    @Query("UPDATE GeneratedOtp g " +
            "SET g.confirmedAt = ?2 " +
            "WHERE g.otp = ?1")
    int updateConfirmedAt(String otp,
                          LocalDateTime confirmedAt);
}
