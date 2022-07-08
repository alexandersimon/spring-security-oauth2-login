package com.example.security.service;

import java.util.Collections;

import com.example.security.repository.PortfolioRepository;
import com.example.security.repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.entity.CryptoUser;
import com.example.security.entity.Portfolio;
import com.example.security.events.UserRegistrationEvent;
import com.example.security.model.UserRegistrationRequest;

@Service
public class UserRegistrationService {

	private final UserRepository userRepository;
	private final PortfolioRepository portfolioRepository;
	private final PasswordEncoder passwordEncoder;
	private final ApplicationEventPublisher eventPublisher;
	
	public UserRegistrationService(UserRepository userRepository, PortfolioRepository portfolioRepository,
			@Lazy PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
		this.userRepository = userRepository;
		this.portfolioRepository = portfolioRepository;
		this.passwordEncoder = passwordEncoder;
		this.eventPublisher = eventPublisher;
	}
	
	public void registerNewUser(UserRegistrationRequest userRegistrationRequest) {
		CryptoUser cryptoUser = new CryptoUser(userRegistrationRequest, passwordEncoder);
		userRepository.save(cryptoUser);
		portfolioRepository.save(new Portfolio(userRegistrationRequest.getUsername(), Collections.EMPTY_LIST));
		eventPublisher.publishEvent(new UserRegistrationEvent(cryptoUser));
	}
	
}
