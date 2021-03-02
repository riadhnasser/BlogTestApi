package com.test.blog.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "POST")
public class Post {
	
	@Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "TITLE")
    private String title;
	
	@Column(name = "TEXT")
    private String text;
	
	@Column(name = "CREATION_DATE")
    private Date creationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User author;
	
	@OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<Comment> commentsList;

}
