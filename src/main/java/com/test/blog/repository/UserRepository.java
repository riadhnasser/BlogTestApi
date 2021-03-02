package com.test.blog.repository;
import org.springframework.data.repository.CrudRepository;
import com.test.blog.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
