package com.example.post.model.posts;

import java.time.LocalDateTime;

import com.example.post.model.users.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostEditForm {
	private Long id;
	@NotBlank
	private String title;
	@NotBlank
	private String content;
	private User user;
	private int views;
	private LocalDateTime createTime;
}
