package com.test.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.blog.dto.PostDTO;
import com.test.blog.dto.Response;
import com.test.blog.dto.UserDTO;
import com.test.blog.service.PostService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "Post", description = "APIs related to post")
public class PostController {

	@Autowired
    private PostService postService;
	
	/**
     * Handles the incoming POST API "/v1/post"
     *
     * @param postDto
     * @return
     */
	@PostMapping("/post")
	public Response savePost(@RequestBody PostDTO postDto) {
        return Response.ok().setPayload(postService.savePost(postDto));
    }
	
	/**
     * Handles the incoming Get API "/v1/post"
     *
     * @param postDto
     * @return
     */
	@GetMapping("/posts")
	public Response getAllPosts() {
        return Response.ok().setPayload(postService.fetchAllPosts());
    }
	
}
