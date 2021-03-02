package com.test.blog.model;

import javax.persistence.*;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "COMMENT")
public class Comment {
	
	@Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "TEXT")
    private String text;
	
	@Column(name = "CREATION_DATE")
    private Date creationDate;

	@Column(name = "UPDATE_DATE")
    private Date updateDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User author;
	
    @Column(name = "POST_ID")
    private Long postId;

}
