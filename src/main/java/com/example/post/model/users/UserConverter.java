package com.example.post.model.users;

public class UserConverter {
	public static User userRegisterFormToUser(UserRegisterForm userRegisterForm) {
		User user = new User();
		user.setUsername(userRegisterForm.getUsername());
		user.setPassword(userRegisterForm.getPassword());
		user.setName(userRegisterForm.getName());
		user.setGender(userRegisterForm.getGender());
		user.setBirthDate(userRegisterForm.getBirthDate());
		user.setEmail(userRegisterForm.getEmail());
		
		return user;
	}
}
