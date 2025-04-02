package com.suraj.service;

import com.suraj.dto.LoginRequest;
import com.suraj.dto.LoginResponse;
import com.suraj.dto.UserDto;

public interface UserService {
	
	public Boolean regitster(UserDto userDto, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

}
