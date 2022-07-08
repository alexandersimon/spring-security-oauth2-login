package com.example.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.security.entity.CryptoCurrency;

public interface CryptoCurrencyRepository extends MongoRepository<CryptoCurrency, String>{
	
	CryptoCurrency findBySymbol(String symbol);
	
}
