package com.sp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sp.config.JwtProvider;
import com.sp.exception.UserException;
import com.sp.model.Cart;
import com.sp.model.User;
import com.sp.repository.UserRepository;
import com.sp.request.LoginRequest;
import com.sp.response.AuthResponse;
import com.sp.service.CartService;
import com.sp.service.CustomUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private CustomUserServiceImplementation customeUserService;
	private PasswordEncoder passwordEncoder;
	private CartService cartService;

	public AuthController(UserRepository userRepository, JwtProvider jwtProvider,CartService cartService,
			CustomUserServiceImplementation customeUserService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.customeUserService = customeUserService;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.cartService = cartService;
	}
	
	@PostMapping(value = "/signup", produces = "application/json")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException

	{
		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist != null) {
			throw new UserException("Email is Already Used With Another Accounts");
		}

		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);

		User savedUser = userRepository.save(createdUser);
		
		Cart cart=cartService.createCart(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);
		AuthResponse authresponse = new AuthResponse();
		authresponse.setJwt(token);
		authresponse.setMessage("Signup Sucess");
		return new ResponseEntity<AuthResponse>(authresponse, HttpStatus.CREATED);

	}

	@PostMapping("/signin")

	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Authentication authentication = authenticate(username, password);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		AuthResponse authresponse = new AuthResponse(); 
		authresponse.setJwt(token);
		authresponse.setMessage("Signin Sucess");

		return new ResponseEntity<AuthResponse>(authresponse, HttpStatus.CREATED);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customeUserService.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid Username");
		}

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid Password....");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
