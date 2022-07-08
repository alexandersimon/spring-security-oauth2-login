package com.example.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.security.repository.CryptoCurrencyRepository;
import org.springframework.stereotype.Service;

import com.example.security.entity.CryptoCurrency;
import com.example.security.model.CryptoCurrencyDto;

@Service
public class CurrencyQueryServiceNoSql implements CurrencyQueryService {

	private final CryptoCurrencyRepository cryproRepository;
	private Map<String, CryptoCurrencyDto> cryptoCurrencies = null;

	
	
	public CurrencyQueryServiceNoSql(CryptoCurrencyRepository cryproRepository) {
		this.cryproRepository = cryproRepository;
	}

	@Override
	public List<CryptoCurrencyDto> getSupportedCryptoCurrencies() {
		if(this.cryptoCurrencies == null) {
			this.cryptoCurrencies = new HashMap<>();
			for(CryptoCurrency currency : cryproRepository.findAll()) {
				this.cryptoCurrencies.put(currency.getSymbol(), new CryptoCurrencyDto(currency.getSymbol(), currency.getName()));
			}
		}
		return new ArrayList<>(cryptoCurrencies.values());
	}

	@Override
	public CryptoCurrencyDto getCryptoCurrency(String symbol) {
		return cryptoCurrencies.get(symbol);
	}
	
}
