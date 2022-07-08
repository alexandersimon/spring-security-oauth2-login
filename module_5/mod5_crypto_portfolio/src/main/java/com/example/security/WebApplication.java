package com.example.security;

import com.example.security.entity.CryptoCurrency;
import com.example.security.model.UserRegistrationRequest;
import com.example.security.service.UserRegistrationService;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.event.EventListener;

import com.example.security.repository.CryptoCurrencyRepository;

@SpringBootApplication
public class WebApplication {

	private final CryptoCurrencyRepository cryptoRepository;
	private final UserRegistrationService userRegistrationService;
	
	public WebApplication(CryptoCurrencyRepository cryptoRepository,UserRegistrationService userRegistrationService) {
		this.cryptoRepository = cryptoRepository;
		this.userRegistrationService=userRegistrationService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void intializeUserData() {	
		CryptoCurrency bitcoin = new CryptoCurrency("BTC", "Bitcoin");
		CryptoCurrency litecoin = new CryptoCurrency("LTC", "Litecoin");
		cryptoRepository.save(bitcoin);
		cryptoRepository.save(litecoin);
		UserRegistrationRequest regRequest = new UserRegistrationRequest();
		regRequest.setUsername("victoria");
		regRequest.setFirstname("Victoria");
		regRequest.setLastname("Smith");
		regRequest.setEmail("vic@email.com");
		this.userRegistrationService.registerNewUser(regRequest);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
	//@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setRedirectPort(8443);
        return connector;
    }
	
}