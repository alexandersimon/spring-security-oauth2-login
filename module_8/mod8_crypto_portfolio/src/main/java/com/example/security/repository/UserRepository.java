package com.example.security.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.security.entity.CryptoUser;

public interface UserRepository extends MongoRepository<CryptoUser, String> {

	Optional<CryptoUser> findByUsername(String username);
	Optional<CryptoUser> findByEmail(String email);

}