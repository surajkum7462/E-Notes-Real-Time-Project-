package com.suraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.LoginRequest;
import com.suraj.dto.LoginResponse;
import com.suraj.dto.UserDto;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto,HttpServletRequest request) throws Exception {
		
		String url=CommonUtil.getUrl(request);
      	Boolean register = userService.regitster(userDto,url);
		if (register) {
			return CommonUtil.createBuildResponeMessage("Register Successfull !Please check your email for verification", HttpStatus.OK);
		} else {
			return CommonUtil.createErrorResponeMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
	
		LoginResponse response =userService.login(loginRequest);
		if(ObjectUtils.isEmpty(response))
		{
			return CommonUtil.createErrorResponeMessage("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtil.createBuildRespone(response, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
