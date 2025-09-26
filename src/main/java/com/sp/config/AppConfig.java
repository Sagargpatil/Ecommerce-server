package com.sp.config;

import java.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

//Summary of What This Code Does:
//Session Management: The app doesn't store sessions between requests (stateless).
//
//Access Control: Requests to /api/** require authentication, while other requests don't.
//
//JWT Token Validation: Before authenticating with username and password, the system checks for a valid JWT token to confirm the user’s identity.
//
//CORS: It restricts API access to specific origins (http://localhost:3000) to prevent unauthorized access from other websites.
//
//Authentication: The app supports both basic authentication and form-based login.
//
//Password Security: Passwords are securely hashed using BCrypt before storing them.
//
//Real-Life Example Scenario:
//Imagine you have a mobile app (front-end) running on http://localhost:3000.
//This app interacts with a backend API that has endpoints starting with /api/. 
//The app sends a login request with a username and password, and if valid, the backend responds with a JWT token. 
//Every time the app calls any /api/** endpoint, it sends this token to authenticate the request. 
//The backend only allows certain users to access these API endpoints and ensures security by encrypting passwords and validating JWT tokens.

@Configuration
public class AppConfig {

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeHttpRequests(
						Authorize -> Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(new JWTValidator(), BasicAuthenticationFilter.class).csrf().disable().cors()
				.configurationSource(new CorsConfigurationSource() {

					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration cfg = new CorsConfiguration();
						cfg.setAllowedOrigins(Arrays.asList(
								
								"http://localhost:3000", 
								"http://localhost:4000",
								"http://localhost:4200",
								"https://codewithsp-ecommerce.vercel.app"));
						cfg.setAllowedMethods(Collections.singletonList("*"));
						cfg.setAllowCredentials(true);
						cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
						cfg.setMaxAge(3600L);
						return cfg;
					}

				}).and()
				.httpBasic()
				.and()
				.formLogin();
		return http.build();
	}

	// To save password in db by encryting as #### so below method
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
