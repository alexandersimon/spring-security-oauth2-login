package com.example.security.service;

import java.util.List;
import java.util.Optional;

import com.example.security.model.ListTransactionsDto;
import com.example.security.model.PortfolioPositionsDto;

public interface PortfolioQueryService {

	Optional<ListTransactionsDto> getPortfolioTransactionsForUser(String username);
	Optional<PortfolioPositionsDto> getPortfolioPositions(String id);
	List<String> getPortfolioIds();
	Optional<PortfolioPositionsDto> getPortfolioPositionsForUser(String username);
	
}
