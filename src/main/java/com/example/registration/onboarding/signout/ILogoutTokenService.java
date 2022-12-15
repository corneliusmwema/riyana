/*
* @Author: Devashish Ashok Pathrabe
* Modified Date: 26-08-2022
* Description: Logout Token Service Interface
*/

package com.example.registration.onboarding.signout;

import java.util.List;

public interface ILogoutTokenService {

	public boolean checkIfTokenExists(String token);
	public LogoutToken createToken(LogoutToken logoutToken);
	public List<LogoutToken> createTokens(List<LogoutToken> logoutTokenList);
	public List<LogoutToken> getAllTokens();
	public void deleteAllTokens();

}
