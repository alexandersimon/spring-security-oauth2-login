package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.security.entity.Portfolio;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
	Optional<Portfolio> findByUsername(String username);
	boolean existsByUsername(String username);
}
