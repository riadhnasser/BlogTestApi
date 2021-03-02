package com.test.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.blog.model.Comment;
import com.test.blog.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDTO {
	
	private Long id;
	
	private String title;
	
	private String text;
	
    private Date creationDate;
	    
    private UserDTO author;
    
    private Long authorId;
	
    private List<CommentDTO> commentsList;
	
}
