package com.example.security.service;

import com.example.security.model.AddTransactionToPortfolioDto;
import com.example.security.model.UserCommandWrapper;

public interface PortfolioCommandService {

	void createNewPortfolio(String username);
	boolean userHasAportfolio(String username);
	void addTransactionToPortfolio(UserCommandWrapper<AddTransactionToPortfolioDto> command);
	void removeTransactionFromPortfolio(UserCommandWrapper<String> command);
	
}

