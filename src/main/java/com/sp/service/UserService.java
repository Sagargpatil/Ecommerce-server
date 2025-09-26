package com.sp.service;



import com.sp.exception.UserException;
import com.sp.model.User;


public interface UserService {
	
	public User findUserById(Long userid) throws UserException;
	public User findUserProfileByJwt(String jwt) throws UserException; 

}
