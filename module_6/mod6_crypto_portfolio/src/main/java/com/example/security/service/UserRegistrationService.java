package com.example.security.service;

import java.util.Collections;

import com.example.security.repository.PortfolioRepository;
import com.example.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.example.security.entity.CryptoUser;
import com.example.security.entity.Portfolio;
import com.example.security.model.UserRegistrationRequest;

@Service
public class UserRegistrationService {

	private final UserRepository userRepository;
	private final PortfolioRepository portfolioRepository;
	
	public UserRegistrationService(UserRepository userRepository, PortfolioRepository portfolioRepository) {
		this.userRepository = userRepository;
		this.portfolioRepository = portfolioRepository;
	}
	
	public void registerNewUser(UserRegistrationRequest userRegistrationRequest) {
		CryptoUser cryptoUser = new CryptoUser(userRegistrationRequest);
		userRepository.save(cryptoUser);
		portfolioRepository.save(new Portfolio(userRegistrationRequest.getUsername(), Collections.EMPTY_LIST));
	}
	
}
