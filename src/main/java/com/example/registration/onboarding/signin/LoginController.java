package com.example.registration.onboarding.signin;

import com.example.registration.onboarding.signout.LogoutToken;
import com.example.registration.onboarding.signout.LogoutTokenServiceImp;
import com.example.registration.security.jwts.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	LogoutTokenServiceImp logoutTokenServiceImp;

	@PostMapping("/login/phone")
	public ResponseEntity<LoginResponse> generateToken(@RequestBody SigninRequest authRequest) throws Exception{
		try {
			authenticationManager.authenticate(new
					UsernamePasswordAuthenticationToken(authRequest.getPhone(), authRequest.getPassword()));
		}catch(Exception exception){ throw new Exception("Wrong login credentials provided, " + "Kindly recheck and try again");}
		String token = jwtUtil.generateToken(authRequest.getPhone());
		LoginResponse data = new LoginResponse(token);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	@PostMapping("login/email")
	public ResponseEntity<LoginResponse> generateTokenEmail(@RequestBody LoginRequest authRequest) throws Exception{
		try {
			authenticationManager.authenticate(new
					UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		}catch(Exception exception){ throw new Exception("Wrong login credentials provided, " + "Kindly recheck and try again");	}
		String token = jwtUtil.generateToken(authRequest.getEmail());
		LoginResponse data = new LoginResponse(token);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/signout")
	public ResponseEntity<String> logoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		if (authorizationHeader.startsWith("Bearer ")) {
			String jwtToken = authorizationHeader.substring(7);
			logoutTokenServiceImp.createToken(new LogoutToken(jwtToken));
			return ResponseEntity.ok("You have been successfully logged out.");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authorization header does not begin with Bearer string.");
		}
	}

}
