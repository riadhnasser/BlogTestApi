package com.test.blog.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.blog.model.Post;
import com.test.blog.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDTO {
	
	private Long id;
	
    private String text;
	
    private Date creationDate;

    private Date updateDate;
	    
    private UserDTO author;
    
    private Long authorId;
	
    private Long postId;

}
