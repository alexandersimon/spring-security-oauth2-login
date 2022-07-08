package com.example.security.repository;

import java.util.Optional;

import com.example.security.entity.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
	Optional<Portfolio> findByUsername(String username);
	boolean existsByUsername(String username);
}
