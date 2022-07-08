package com.example.security.service;

import java.util.List;

import com.example.security.model.CryptoCurrencyDto;

public interface CurrencyQueryService {

	List<CryptoCurrencyDto> getSupportedCryptoCurrencies();
	CryptoCurrencyDto getCryptoCurrency(String symbol);
}
