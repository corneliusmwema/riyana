package com.example.registration.onboarding.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserFarmerRepository extends JpaRepository<UserFarmer, Long> {
    @Query("SELECT user FROM UserFarmer user WHERE user.phone=?1")
    UserFarmer findByPhone(String phone);

    @Override
    List<UserFarmer> findAll();


    @Transactional
    @Modifying
    @Query("UPDATE UserFarmer a " +
            "SET a.enabled = TRUE WHERE a.phone = ?1")
    int enableUserFarmer(String phone);

    @Transactional
    @Modifying
    @Query("UPDATE UserFarmer s " +
            "SET s.password = ?1 WHERE s.phone = ?2")
    void changeUserPassword(String phone);
}
