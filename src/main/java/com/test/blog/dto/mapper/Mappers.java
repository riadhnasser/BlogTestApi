package com.test.blog.dto.mapper;



import java.util.stream.Collectors;

import com.test.blog.dto.CommentDTO;
import com.test.blog.dto.PostDTO;
import com.test.blog.dto.UserDTO;
import com.test.blog.model.Comment;
import com.test.blog.model.Post;
import com.test.blog.model.User;

public class Mappers {
	public static CommentDTO commentToCommentDTO(Comment comment) {
		return new CommentDTO()
                .setId(comment.getId())
                .setCreationDate(comment.getCreationDate())
                .setAuthor(userToUserDTO(comment.getAuthor()))
                .setPostId(comment.getPostId())
                .setText(comment.getText());
	}
	
	public static UserDTO userToUserDTO(User user) {
		return new UserDTO()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setPassword(user.getPassword());
	}
	
	public static PostDTO postToPostDTO(Post post) {
		PostDTO response = new PostDTO();
		response.setId(post.getId());
		response.setTitle(post.getTitle());
		response.setCreationDate(post.getCreationDate());
		response.setAuthor(userToUserDTO(post.getAuthor()));
		response.setText(post.getText());
		if (post.getCommentsList() != null) {
			response.setCommentsList(post.getCommentsList()
            		.stream()
            		.map(el -> commentToCommentDTO(el)).collect(Collectors.toList()));
		}
		return response;
	}

}
