package com.example.security.repository;

import java.util.List;

import com.example.security.entity.SupportQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportQueryRepository extends MongoRepository<SupportQuery, String>{
	
	List<SupportQuery> findByUsername(String username);
	
}
