package com.example.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.authn.CustomUserDetails;
import com.example.security.entity.CryptoUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<CryptoUser> userOptional = this.userRepository.findByUsername(username);
		return userOptional.map(user -> {
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
			authList.add(new SimpleGrantedAuthority("USER"));
			return new CustomUserDetails(user, authList);
		}).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
