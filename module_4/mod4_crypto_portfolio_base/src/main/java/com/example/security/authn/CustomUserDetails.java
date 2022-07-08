package com.example.security.authn;

import java.util.Collection;

import com.example.security.entity.CryptoUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private final String firstname;
	private final String lastname;
	private final String email;
	
	public CustomUserDetails(CryptoUser user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
		this.firstname=user.getFirstName();
		this.lastname=user.getLastName();
		this.email=user.getEmail();
	}

	public String getEmail() {
		return email;
	}


}
