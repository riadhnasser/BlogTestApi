package com.test.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.blog.dto.UserDTO;
import com.test.blog.dto.mapper.DtoMappers;
import com.test.blog.dto.mapper.Mappers;
import com.test.blog.model.User;
import com.test.blog.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserDTO signUp(UserDTO userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
        	User newUser = new User();
        	newUser.setEmail(userDto.getEmail());
        	newUser.setUsername(userDto.getEmail());
        	newUser.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        	newUser.setFirstName(userDto.getFirstName());
        	newUser.setLastName(userDto.getLastName());
            return Mappers.userToUserDTO(userRepository.save(newUser));

        }
        UserDTO response = new UserDTO();
        response.setMessage("Erreur lors de création");
        return response;

	}
	
	public UserDTO findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
            return Mappers.userToUserDTO(user);
		
	}
	
	public UserDTO getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
		  String username = ((UserDetails)principal).getUsername();
		} else {
		  String username = principal.toString();
		}
        User userDetails = (User) auth.getPrincipal();
        return findUserByEmail(userDetails.getUsername());
	}


}
