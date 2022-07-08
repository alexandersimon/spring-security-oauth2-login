package com.example.security.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import com.example.security.repository.CryptoCurrencyRepository;
import com.example.security.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import com.example.security.entity.CryptoCurrency;
import com.example.security.entity.Portfolio;
import com.example.security.entity.Transaction;
import com.example.security.entity.Type;
import com.example.security.model.AddTransactionToPortfolioDto;
import com.example.security.model.UserCommandWrapper;

@Service
public class PortfolioCommandServiceNoSql implements PortfolioCommandService {

	private final PortfolioRepository portfolioRepository;
	private final CryptoCurrencyRepository currencyRepository;
	
	
	
	public PortfolioCommandServiceNoSql(PortfolioRepository portfolioRepository,
			CryptoCurrencyRepository currencyRepository) {
		this.portfolioRepository = portfolioRepository;
		this.currencyRepository = currencyRepository;
	}

	@Override
	public void addTransactionToPortfolio(UserCommandWrapper<AddTransactionToPortfolioDto> command) {
		Optional<Portfolio> portfolioOptional = portfolioRepository.findByUsername(command.getUserDetails().getUsername());
		portfolioOptional.ifPresent(portfolio -> {
			Transaction transaction = createTransactionEntity(command.getDto());
			portfolio.addTransaction(transaction);
			portfolioRepository.save(portfolio);
		});
	}

//	public void addTransactionToPortfolio(UserCommandWrapper<List<AddTransactionToPortfolioDto>> command) {
//		Portfolio portfolio = portfolioRepository.findByUsername(command.getUserDetails().getUsername());
//		for(AddTransactionToPortfolioDto transactionDto : command.getRequestDto()) {
//			Transaction transaction = createTransactionEntity(transactionDto);
//			portfolio.addTransaction(transaction);
//		}
//		portfolioRepository.save(portfolio);
//	}
	
	@Override
	public void removeTransactionFromPortfolio(UserCommandWrapper<String> command) {
		Optional<Portfolio> portfolioOptional = portfolioRepository.findByUsername(command.getUserDetails().getUsername());
		portfolioOptional.ifPresent(portfolio -> {
			Transaction transacion = portfolio.getTransactionById(command.getDto());
			portfolio.deleteTransaction(transacion);
			portfolioRepository.save(portfolio);
		});	
	}
	
	@Override
	public void createNewPortfolio(String username) {
		portfolioRepository.save(new Portfolio(username, new ArrayList<>()));
	}
	
	private Transaction createTransactionEntity(AddTransactionToPortfolioDto request) {
		CryptoCurrency crpytoCurrency = currencyRepository.findBySymbol(request.getCryptoSymbol());
		Type type = Type.valueOf(request.getType());
		BigDecimal quantity = new BigDecimal(request.getQuantity());
		BigDecimal price = new BigDecimal(request.getPrice());
		Transaction transaction = new Transaction(crpytoCurrency, type, quantity, price,System.currentTimeMillis());
		return transaction;
	}

	@Override
	public boolean userHasAportfolio(String username) {
		return this.portfolioRepository.existsByUsername(username);
	}

}
