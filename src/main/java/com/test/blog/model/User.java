package com.test.blog.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "USER")
public class User {
	
	@Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "EMAIL")
    private String email;
	
	@Column(name = "USERNAME")
    private String username;
	
	@Column(name = "FIRST_NAME")
    private String firstName;
	
	@Column(name = "LAST_NAME")
    private String lastName;
	
	@Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "REGISTEREDAT")
    private Long registeredAt;
}
