package com.example.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.post.model.posts.Post;
import com.example.post.model.users.User;
import com.example.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
	/*
	 * 의존성 주입(DI)
	 * 1. 필드 주입
	 * 2. 생성자 주입
	 * 3. setter 주입
	 */
//	@Autowired
	private final PostRepository postRepository;
	
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
		log.info("PostServiceImpl 객체 생성");
	}
	
//	@Autowired
//	public void setPostRepository(PostRepository postRepository) {
//		this.postRepository = postRepository;
//		log.info("PostServiceImpl setter 호출");
//	}

	@Override
	public Post savePost(Post post) {
		post.setCreateTime(LocalDateTime.now());
		postRepository.savePost(post);
		return post;
	}

	@Override
	public List<Post> getAllPosts() {
		return postRepository.findAllPosts();
	}

	@Override
	public Post getPostById(Long postId) {
		Post post = postRepository.findPostById(postId);
		if (post != null) {
			post.setViews(post.getViews() + 1);
			postRepository.updatePost(post);			
		}
		return post;
	}

	@Override
	public void removePost(Long postId, User loginUser) {
		Post post = postRepository.findPostById(postId);
		if (post != null && loginUser != null) {
			// 게시글의 작성자의 아이디와 로그인 유저의 아이디가 같으면 삭제
			if (post.getUser().getId().equals(loginUser.getId())) {
				postRepository.removePost(postId);
			}
		}
	}
	
	@Override
	public Post updatePost(Post post) {
		postRepository.updatePost(post);
		return post;
	}

}









