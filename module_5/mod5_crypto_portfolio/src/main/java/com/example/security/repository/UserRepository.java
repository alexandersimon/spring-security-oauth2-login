package com.example.security.repository;

import java.util.Optional;

import com.example.security.entity.CryptoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<CryptoUser, String> {

	Optional<CryptoUser> findByUsername(String username);
	Optional<CryptoUser> findByEmail(String email);

}