/*
* @Author: Devashish Ashok Pathrabe
* Modified Date: 26-08-2022
* Description: Logout Token Entity
*/

package com.example.registration.onboarding.signout;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "logout_token")
public class LogoutToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotEmpty
	@Size(max = 512)
	private String token;

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LogoutToken(@NotNull @NotEmpty String token) {
		super();
		this.token = token;
	}

	public LogoutToken() {
		super();
	}

}
