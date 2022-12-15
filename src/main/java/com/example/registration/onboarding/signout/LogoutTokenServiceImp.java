package com.example.registration.onboarding.signout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogoutTokenServiceImp implements ILogoutTokenService {

	@Autowired
	LogoutTokenRepository logoutTokenRepository;

	@Override
	public boolean checkIfTokenExists(String token) {
		return logoutTokenRepository.existsByToken(token);
	}
	@Override
	public LogoutToken createToken(LogoutToken logoutToken) {
		return logoutTokenRepository.save(logoutToken);
	}


	@Override
	public List<LogoutToken> createTokens(List<LogoutToken> logoutTokenList) {
		return logoutTokenRepository.saveAll(logoutTokenList);
	}

	@Override
	public List<LogoutToken> getAllTokens() {
		return logoutTokenRepository.findAll();
	}

	@Override
	public void deleteAllTokens() {
		logoutTokenRepository.deleteAllInBatch();
	}

}
