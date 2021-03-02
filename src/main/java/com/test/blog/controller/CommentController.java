package com.test.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.blog.dto.CommentDTO;
import com.test.blog.dto.PostDTO;
import com.test.blog.dto.Response;
import com.test.blog.dto.UserDTO;
import com.test.blog.service.CommentService;
import com.test.blog.service.PostService;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(value = "Post", description = "APIs related to comment")
public class CommentController {

	@Autowired
    private CommentService commentService;
	
	/**
     * Handles the incoming POST API "/api/comment"
     *
     * @param commentDTO
     * @return
     */
	@PostMapping("/comment")
	public Response saveComment(@RequestBody CommentDTO commentDto) {
        return Response.ok().setPayload(commentService.saveComment(commentDto));
    }
	
	/**
     * Handles the incoming Get API "/api/comments"
     *
     * @param commentId
     * @param authorEmail
     * @return
     */
	@GetMapping("/comments/{commentId}/{authorEmail}")
	public Response deleteComment(@PathVariable( "commentId" ) final Long commentId,
			@PathVariable( "authorEmail" ) final String authorEmail) {
        return Response.ok().setPayload(commentService.deleteComment(commentId, authorEmail));
    }
	
}
