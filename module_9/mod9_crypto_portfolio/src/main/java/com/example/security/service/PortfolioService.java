package com.example.security.service;

import java.util.List;

import com.example.security.entity.CryptoCurrency;
import com.example.security.entity.Portfolio;
import com.example.security.model.AddTransactionToPortfolioDto;
import com.example.security.model.ListTransactionsDto;
import com.example.security.model.PortfolioPositionsDto;

public interface PortfolioService {

	List<CryptoCurrency> getSupportedCryptoCurrencies();
	Portfolio getPortfolioForUsername(String username);
	PortfolioPositionsDto getPortfolioPositions(String username);
	void addTransactionToPortfolio(AddTransactionToPortfolioDto request);
	ListTransactionsDto getPortfolioTransactions(String username);
	void removeTransactionFromPortfolio(String username, String transactionId);
	void createNewPortfolio(String username);
}
