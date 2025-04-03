package com.suraj.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.suraj.entity.User;

public interface JWTService {
	
	public String generateToken(User user);
	
	public String extractUserName(String token);
	
	public Boolean validateToken(String token,UserDetails userDetails);

}
