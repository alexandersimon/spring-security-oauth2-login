package com.example.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.security.service.PortfolioQueryService;
import com.example.security.service.UserRegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.security.model.UserRegistrationRequest;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{
	
	private PortfolioQueryService portfolioQueryService;
	private UserRegistrationService userRegistrationService;
	
	public AuthenticationSuccessHandlerImpl(PortfolioQueryService portfolioQueryService,
			UserRegistrationService userRegistrationService) {
		this.portfolioQueryService = portfolioQueryService;
		this.userRegistrationService = userRegistrationService;
	}


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		if(this.portfolioQueryService.getPortfolioPositionsForUser(authentication.getName()).isEmpty()) {
			UserRegistrationRequest registrationRequest = new UserRegistrationRequest();
			OidcUser oidcUser = (OidcUser)authentication.getPrincipal();
			registrationRequest.setUsername(authentication.getName());
			registrationRequest.setEmail(oidcUser.getEmail());
			registrationRequest.setFirstname(oidcUser.getGivenName());
			registrationRequest.setLastname(oidcUser.getFamilyName());
			this.userRegistrationService.registerNewUser(registrationRequest);
		}
		response.sendRedirect("/portfolio");
	}

}
