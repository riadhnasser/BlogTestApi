package com.test.blog.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;

import com.test.blog.dto.UserDTO;
import com.test.blog.dto.mapper.Mappers;
import com.test.blog.model.User;
import com.test.blog.repository.UserRepository;
import com.test.blog.service.exceptions.InvalidAccessTokenException;
import com.test.blog.service.exceptions.LoginFailedException;
import com.test.blog.util.TokenUtils;

@Service
public class AuthService {
	
    private static final Map<String, AccessToken> accessTokens = new ConcurrentHashMap<>();
    private final long accessTokenLifetime = 600000;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	@Autowired private UserRepository userRepository;

	
    /**
     * Validates user credentials and generates access and refresh tokens
     *
     * @param request LoginRequest containing user credentials.
     * @return LoginTransfer containing access and refresh tokens along with user information.
     */
    public UserDTO login(UserDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null || request.getPassword() == null || !bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new LoginFailedException();
        
        UserDTO response = Mappers.userToUserDTO(user);
        response.setAccessToken(TokenUtils.encodeBase64(createAccessTokenForUser(user)));
        return response;
    }
    
    /**
     * Creates new user 
     *
     * @param request RegisterRequest containing user information.
     * @return Created user record.
     */
    public UserDTO register(UserDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            User newUser = createUserByRegistrationRequest(request);
            return Mappers.userToUserDTO(newUser);

        }
        UserDTO response = new UserDTO();
        response.setMessage("Erreur lors de création");
        return response;
    }
    
    private User createUserByRegistrationRequest(UserDTO request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRegisteredAt(System.currentTimeMillis());
        userRepository.save(user);
        return user;
    }
    
	/**
     * Gets user if it is associated with an access token.
     * @param accessToken Access token to be checked for its user association.
     * @return User object associated with an access token.
     */
    public User getUserByValidAccessToken(String accessToken) {
        if (!verifyAccessToken(accessToken) || accessTokens.get(accessToken).getUser() == null) {
            throw new InvalidAccessTokenException();
        }
        return accessTokens.get(accessToken).getUser();
    }
    
    private boolean verifyAccessToken(String token) {
        return token != null && accessTokens.containsKey(token) && accessTokens.get(token).getValidUntilInEpoch() > System.currentTimeMillis();
    }
    
    private String createAccessTokenForUser(User user) {
        AccessToken accessToken = new AccessToken(user);

        accessTokens.put(accessToken.getToken(), accessToken);

        return accessToken.getToken();
    }
    
    @Getter
    private class AccessToken {
        private final String token;
        private final long validUntilInEpoch;
        private final User user;

        AccessToken(User user) {
            this.user = user;
            this.token = TokenUtils.generateToken();
            this.validUntilInEpoch = System.currentTimeMillis() + accessTokenLifetime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            AccessToken that = (AccessToken) o;

            return token.equals(that.token);
        }

        @Override
        public int hashCode() {
            return token.hashCode();
        }
    }

}
