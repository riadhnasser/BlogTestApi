package com.test.blog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.test.blog.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
	
	List<Post> findByAuthorId(Long authorId);
	
}