package com.example.post.service;

import com.example.post.model.users.User;

public interface UserService {

	// 회원가입
	User registerUser(User user);
	
	// 로그인 (username으로 회원정보 조회)
	User findUserByUsername(String username);
	
}
