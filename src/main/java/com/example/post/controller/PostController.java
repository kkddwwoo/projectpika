package com.example.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.post.model.posts.Post;
import com.example.post.model.posts.PostConverter;
import com.example.post.model.posts.PostCreateForm;
import com.example.post.model.posts.PostEditForm;
import com.example.post.model.users.User;
import com.example.post.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("posts")
@Controller
public class PostController {
	
	private final PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}

	// 게시글 목록 조회
	@GetMapping
	public String listPosts(Model model) {
		List<Post> posts = postService.getAllPosts();
		for (Post post : posts) {
			log.info("{}", post);
		}
		model.addAttribute("posts", posts);
		
		return "posts/list";
	}
	
	// 게시글 상세 조회
	@GetMapping("{postId}")
	public String viewPost(@PathVariable(name = "postId") Long postId,
						   Model model) {
		Post post = postService.getPostById(postId);
		model.addAttribute("post", post);
		
		return "posts/view";
	}
	
	// 게시글 작성 폼
	@GetMapping("create")
	public String createPostForm(@SessionAttribute(name = "loginUser", required = false) User loginUser,
								 Model model) {
		model.addAttribute("postCreateForm", new PostCreateForm());
		return "posts/create";
	}
	
	// 게시글 작성 처리
	@PostMapping("create")
	public String createPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
							 @Validated @ModelAttribute PostCreateForm postCreateForm,
							 BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "posts/create";
		}
		
		Post post = PostConverter.postCreateFormToPost(postCreateForm);
		post.setUser(loginUser);
		postService.savePost(post);
	
		return "redirect:/posts";
	}
	
	// 게시글 삭제
	@GetMapping("remove/{postId}")
	public String removePost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
							 @PathVariable(name = "postId") Long postId) {
		postService.removePost(postId, loginUser);
		return "redirect:/posts";
	}
	
	// 게시글 수정 폼
	@GetMapping("edit/{postId}")
	public String editPostForm(@SessionAttribute(name = "loginUser", required = false) User loginUser,
							   @PathVariable(name = "postId") Long postId,
							   Model model) {
		Post post = postService.getPostById(postId);
		if (post == null || !post.getUser().getId().equals(loginUser.getId())) {
			log.info("수정 권한 없음");
			return "redirect:/posts";
		}
		
		PostEditForm postEditForm = PostConverter.postToPostEditForm(post);
		model.addAttribute("postEditForm", postEditForm);
		return "posts/edit";
	}
	
	// 게시글 수정
	@PostMapping("edit/{postId}")
	public String editPost(@SessionAttribute(name = "loginUser", required = false) User loginUser,
						   @PathVariable(name = "postId") Long postId,
						   @Validated @ModelAttribute PostEditForm postEditForm,
						   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "posts/edit";
		}
		
		Post findPost = postService.getPostById(postId);
		if (findPost == null || !findPost.getUser().getId().equals(loginUser.getId())) {
			log.info("수정 권한 없음");
			return "redirect:/posts";
		}
		postEditForm.setId(postId);
		Post post = PostConverter.postEditFormToPost(postEditForm);
		postService.updatePost(post);
		
		return "redirect:/";
	}
	
}










