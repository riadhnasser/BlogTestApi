package com.test.blog.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.test.blog.dto.CommentDTO;
import com.test.blog.dto.PostDTO;
import com.test.blog.dto.mapper.DtoMappers;
import com.test.blog.dto.mapper.Mappers;
import com.test.blog.model.Comment;
import com.test.blog.model.Post;
import com.test.blog.model.User;
import com.test.blog.repository.CommentRepository;
import com.test.blog.repository.UserRepository;

@Service
public class CommentService {
	
	@Autowired  private CommentRepository commentRepository;
	@Autowired  private UserRepository userRepository;

	/**
     * Create a new comment on a post
     *
     * @param commentDto
     * @return
     */
	public CommentDTO saveComment(CommentDTO commentDto) {
        Optional<User> author = userRepository.findById(commentDto.getAuthorId());

		Comment comment = new Comment()
					.setText(commentDto.getText())
					.setCreationDate(new Date())
					.setUpdateDate(new Date())
					.setPostId(commentDto.getPostId())
					.setAuthor(author.get());
		commentRepository.save(comment);
		return Mappers.commentToCommentDTO(comment);
	}
	
	/**
     * Delete a comment from a post
     *
     * @param commentId
     * @param authorEmail
     * @return
     */
	public CommentDTO deleteComment(Long commentId, String authorEmail) {
		Optional<Comment>  comment = commentRepository.findById(commentId);
		//commentRepository.save(comment);
		return Mappers.commentToCommentDTO(comment.get());
	}

}
