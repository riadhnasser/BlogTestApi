package com.test.blog.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.test.blog.dto.PostDTO;
import com.test.blog.dto.mapper.DtoMappers;
import com.test.blog.dto.mapper.Mappers;
import com.test.blog.model.Post;
import com.test.blog.model.User;
import com.test.blog.repository.PostRepository;
import com.test.blog.repository.UserRepository;

@Service
public class PostService {
	
	@Autowired  private PostRepository postRepository;
	
	@Autowired  private UserRepository userRepository;

	
	/**
     * Create a new blog post
     *
     * @param postDto
     * @return
     */
	public PostDTO savePost(PostDTO postDto) {
        Optional<User> author = userRepository.findById(postDto.getAuthorId());

		
		Post post = new Post()
					.setText(postDto.getText())
					.setCreationDate(new Date())
					.setAuthor(author.get());
		postRepository.save(post);
		return Mappers.postToPostDTO(post);
	}
	
	/**
     * fetch all posts
     *
     * @return
     */
	public List<PostDTO> fetchAllPosts() {
		List<Post> postsList = (List<Post>) postRepository.findAll();
		if (!postsList.isEmpty()) {
			return postsList
                    .stream()
                    .map(p -> Mappers.postToPostDTO(p))
                    .collect(Collectors.toList());
		}
        return Collections.emptyList();
	}

}
