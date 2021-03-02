package com.test.blog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.test.blog.model.Comment;
import com.test.blog.model.Post;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Post> findByPostId(Long postId);
	
	// ERROR List<Post> findByPostIdByAuthorId(Long postId, Long authorId);

}