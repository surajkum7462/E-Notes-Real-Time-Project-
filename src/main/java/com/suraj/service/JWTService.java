package com.suraj.service;

import com.suraj.entity.User;

public interface JWTService {
	
	public String generateToken(User user);

}
