package com.example.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.model.AddTransactionToPortfolioDto;
import com.example.security.model.DeleteTransactionsDto;
import com.example.security.model.UserCommandWrapper;
import com.example.security.service.PortfolioCommandService;

@Controller
public class PortfolioCommandController {

	private final PortfolioCommandService commandService;
	
	public PortfolioCommandController(PortfolioCommandService commandService) {
		this.commandService = commandService;
	}

	@PostMapping("/portfolio/transactions")
	public ModelAndView addTransactionToPortfolio(@ModelAttribute("transaction") AddTransactionToPortfolioDto request, @AuthenticationPrincipal OidcUser principal) {	
		commandService.addTransactionToPortfolio(new UserCommandWrapper<AddTransactionToPortfolioDto>(principal, request));
		return new ModelAndView("redirect:/portfolio");
	}
	
	@DeleteMapping("/portfolio/transactions")
	public ModelAndView deleteTransactionFromPortfolio(@ModelAttribute("selected") DeleteTransactionsDto request, @AuthenticationPrincipal OidcUser principal) {
		for(String id : request.getId()) {
			commandService.removeTransactionFromPortfolio(new UserCommandWrapper<String>(principal, id));
		}
		return new ModelAndView("redirect:/portfolio");
	}
	
}
