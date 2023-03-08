package com.example.registration.onboarding.signin;

import com.example.registration.security.jwts.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> generateToken(@RequestBody SigninRequest authRequest) throws Exception{
		try {
			authenticationManager.authenticate(new
					UsernamePasswordAuthenticationToken(authRequest.getPhone(), authRequest.getPassword()));
		}catch(Exception exception){ throw new Exception("Wrong login credentials provided, " + "Kindly recheck and try again");}
		String token = jwtUtil.generateToken(authRequest.getPhone());
		LoginResponse data = new LoginResponse(token);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
