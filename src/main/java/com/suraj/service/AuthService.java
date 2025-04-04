package com.suraj.service;

import com.suraj.dto.LoginRequest;
import com.suraj.dto.LoginResponse;
import com.suraj.dto.UserRequest;

public interface AuthService {
	
	public Boolean regitster(UserRequest userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
