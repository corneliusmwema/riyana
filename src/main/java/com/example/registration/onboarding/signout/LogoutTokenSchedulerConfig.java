package com.example.registration.onboarding.signout;

import com.example.registration.security.jwts.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class LogoutTokenSchedulerConfig {

	private final Logger log = LoggerFactory.getLogger(LogoutTokenSchedulerConfig.class);

	@Autowired
	private LogoutTokenServiceImp logoutTokenServiceImp;

	@Autowired
	private JwtUtil jwtTokenUtilityComponent;

	@Scheduled(fixedDelay = 15 * 60 * 1000) // 15 Minutes
	public void deleteExpiredJwtTokens() {
		log.info("deleteExpiredJwtTokens scheduled job started...");
	    List<LogoutToken> logoutTokenList = logoutTokenServiceImp.getAllTokens();
	    logoutTokenServiceImp.deleteAllTokens();
	    List<LogoutToken> validLogoutTokenList = new ArrayList<>();
	    for (LogoutToken logoutToken : logoutTokenList) {
	    	try {
	    		if (jwtTokenUtilityComponent.isTokenExpired(logoutToken.getToken())) {
	    			log.info("JWT Token " + logoutToken.getToken() + " removed from logout_token table.");
	    		} else {
	    			validLogoutTokenList.add(logoutToken);
	    		}
	    	} catch (ExpiredJwtException e) {
	    		log.warn("Exception in deleteExpiredJwtTokens - " + e.toString());
	    	}
	    }
	    logoutTokenServiceImp.createTokens(validLogoutTokenList);
	    log.info("deleteExpiredJwtTokens scheduled job finished.");
	}

}
