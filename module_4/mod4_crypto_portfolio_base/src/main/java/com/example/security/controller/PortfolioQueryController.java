package com.example.security.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.security.exceptions.PortfolioNotFoundException;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.authn.CustomUserDetails;
import com.example.security.model.AddTransactionToPortfolioDto;
import com.example.security.model.DeleteTransactionsDto;
import com.example.security.model.TransactionDetailsDto;
import com.example.security.service.PortfolioQueryService;
import com.example.security.service.PricingService;

@Controller
public class PortfolioQueryController {

	private final PortfolioQueryService portfolioService;
	private final PricingService priceService;
		
	public PortfolioQueryController(PortfolioQueryService portfolioService, PricingService priceService) {
		this.portfolioService = portfolioService;
		this.priceService = priceService;
	}

	@GetMapping("/")
	public String index() {
		return "redirect:/portfolio";
	}
		
	@GetMapping("/portfolio")
	public ModelAndView positions(@AuthenticationPrincipal CustomUserDetails principal) {
		
		return this.portfolioService.getPortfolioPositionsForUser(principal.getUsername()).map(positions -> {
			ModelAndView model = new ModelAndView();
			model.addObject("positionsResponse", positions);
			model.addObject("transaction", new AddTransactionToPortfolioDto());
			return model;
		}).orElseThrow(PortfolioNotFoundException::new);

	}
	
	@GetMapping("/portfolio/{id}")
	public ModelAndView userPositions(@PathVariable String id) {
		return this.portfolioService.getPortfolioPositions(id).map(positions -> {
			ModelAndView model = new ModelAndView();
			model.addObject("positionsResponse", positions);
			model.addObject("transaction", new AddTransactionToPortfolioDto());
			model.setViewName("portfolio");
			return model;
		}).orElseThrow(PortfolioNotFoundException::new);		
	}
	
	@GetMapping("/price")
	public ResponseEntity<BigDecimal> priceOfBtc() {
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
				.body(priceService.getCurrentPriceForCrypto("BTC"));
	}
	
	@GetMapping(value = {"/portfolio/transactions"})
	public ModelAndView listTransactionsForPortfolio(@AuthenticationPrincipal CustomUserDetails principal) {
		return portfolioService.getPortfolioTransactionsForUser(principal.getUsername()).map(transactionsDto -> {
			ModelAndView model = new ModelAndView("transactions");
			model.addObject("transactions",transactionsDto.getTransactions());
			model.addObject("selected",new DeleteTransactionsDto());
			return model;
		}).orElseThrow(PortfolioNotFoundException::new);
	}
	
	@GetMapping(value = {"/portfolio/transactions/{symbol}"})
	public ModelAndView listTransactionsForPortfolio(@PathVariable String symbol, @AuthenticationPrincipal CustomUserDetails principal) {
		return portfolioService.getPortfolioTransactionsForUser(principal.getUsername()).map(transactionsDto -> {
			ModelAndView model = new ModelAndView("transactions");
			List<TransactionDetailsDto> transactionDtos = transactionsDto.getTransactions().stream().filter(trans -> symbol.equals(trans.getSymbol())).collect(Collectors.toList());
			model.addObject("transactions",transactionDtos);
			model.addObject("selected",new DeleteTransactionsDto());
			return model;
		}).orElseThrow(PortfolioNotFoundException::new);
	}
	
}