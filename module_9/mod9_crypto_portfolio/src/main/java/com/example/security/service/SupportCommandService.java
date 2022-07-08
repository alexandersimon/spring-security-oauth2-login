package com.example.security.service;

import com.example.security.model.CreateSupportQueryDto;
import com.example.security.model.PostDto;
import com.example.security.model.UserCommandWrapper;

public interface SupportCommandService {

	void createQuery(UserCommandWrapper<CreateSupportQueryDto> query);
	void postToQuery(UserCommandWrapper<PostDto> supportQueryPostModel);
	void resolveQuery(String id);
	
}
