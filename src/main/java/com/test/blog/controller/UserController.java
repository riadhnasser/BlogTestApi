package com.test.blog.controller;

import io.swagger.annotations.Api;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.blog.dto.Response;
import com.test.blog.dto.UserDTO;
import com.test.blog.service.AuthService;
import com.test.blog.service.UserService;


@RestController
@CrossOrigin
@RequestMapping("/api/users")
@Api(value = "User", description = "APIs related to authentication")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
    private AuthService authService;

    /**
     * Handles the incoming POST API "/v1/user/sign-up"
     *
     * @param userSignupRequest
     * @return
     */
	@PostMapping("/sign-up")
	public Response signup(@RequestBody UserDTO userSignupRequest) {
        return Response.ok().setPayload(userService.signUp(userSignupRequest));
    }
	
	/**
     * Handles the incoming POST API "/v1/users/login"
     *
     * @param userSignupRequest
     * @return
     */
	@PostMapping("/login")
	public Response signIn(@RequestBody  UserDTO request) {
		LoginStatus l_status = new LoginStatus();
		l_status.setStatus( "Login success" );
		l_status.setUser(authService.login(request));
        return Response.ok().setPayload(l_status);
    }
	
	@RequestMapping("/register")
    public Response register(@RequestBody UserDTO request) {
        UserDTO user = authService.register(request);
        return Response.ok().setPayload(user);
    }
	
	@Data
	public static class LoginStatus {

		private String status;
		
		private UserDTO user;
	}
	
}
