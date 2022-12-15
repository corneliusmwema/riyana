/*
* @Author: Devashish Ashok Pathrabe
* Modified Date: 26-08-2022
* Description: Logout Token Repository
*/

package com.example.registration.onboarding.signout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogoutTokenRepository extends JpaRepository<LogoutToken, Long> {

	Optional<LogoutToken> findByToken(String token);

	boolean existsByToken(String token);

}
