package com.sp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sp.config.JwtProvider;
import com.sp.exception.UserException;
import com.sp.model.User;
import com.sp.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userid) throws UserException {
		Optional<User> user=userRepository.findById(userid);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("User not found with id: "+userid);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromToken(jwt);
		
		User user= userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not found with email"+ email);
			
		}
		return user;
	}

}
