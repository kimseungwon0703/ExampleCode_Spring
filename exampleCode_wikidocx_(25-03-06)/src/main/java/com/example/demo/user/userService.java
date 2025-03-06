package com.example.demo.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.DataNotFoundException;

@Service
public class userService {

    private final UserRepo userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    public userService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
    	this.userRepository = userRepository;
    	this.passwordEncoder = passwordEncoder;
    }

    public userEntity create(String username, String email, String password) {
    	userEntity user = new userEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
    
    public userEntity getUser(String username) {
    	Optional<userEntity> userEntity = this.userRepository.findByusername(username);
    	if(userEntity.isPresent()) {
    		return userEntity.get();
    	}else {
    		throw new DataNotFoundException("user not found");
    	}
    }
}
