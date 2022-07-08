package com.example.security.service;

import com.example.security.repository.SupportQueryRepository;
import org.springframework.stereotype.Service;

import com.example.security.entity.Post;
import com.example.security.entity.SupportQuery;
import com.example.security.model.CreateSupportQueryDto;
import com.example.security.model.PostDto;
import com.example.security.model.UserCommandWrapper;

@Service
public class SupportCommandServiceNoSql implements SupportCommandService {

	private final SupportQueryRepository supportRepository;
		
	public SupportCommandServiceNoSql(SupportQueryRepository supportRepository) {
		this.supportRepository = supportRepository;
	}

	@Override
	public void createQuery(UserCommandWrapper<CreateSupportQueryDto> command) {
		supportRepository.save(mapModelToEntity(command.getUsername(),command.getDto()));
	}
	
	@Override
	public void postToQuery(UserCommandWrapper<PostDto> command) {
		PostDto postRequest = command.getDto();
		Post post = new Post(command.getUsername() , postRequest.getContent(), System.currentTimeMillis());
		supportRepository.findById(postRequest.getQueryId()).ifPresent(query -> {
			query.addPost(post);
			if(postRequest.isResolve()) {
				query.resolve();
			}
			supportRepository.save(query);
		});
		
	}
	
	@Override
	public void resolveQuery(String id) {
		supportRepository.findById(id).ifPresent(query -> {
			query.resolve();
			supportRepository.save(query);
		});

	}
	
	private SupportQuery mapModelToEntity(String username, CreateSupportQueryDto model) {
		SupportQuery supportQuery = new SupportQuery(username, model.getSubject());
		supportQuery.addPost(model.getContent(), username );
		return supportQuery;
	}
		
}
