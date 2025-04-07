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
import com.suraj.dto.UserRequest;
import com.suraj.service.AuthService;
import com.suraj.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest userDto,HttpServletRequest request) throws Exception {
		log.info("AuthController : registerUser() : Execution Start");
		String url=CommonUtil.getUrl(request);
      	Boolean register = authService.regitster(userDto,url);
		if (register) {
			log.info("AuthController : registerUser() : Execution End");
			return CommonUtil.createBuildResponeMessage("Register Successfull !Please check your email for verification", HttpStatus.OK);
		} else {
			log.info("AuthController : registerUser() : Execution End");
			return CommonUtil.createErrorResponeMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
		log.info("AuthController : login() : Execution Start");
		LoginResponse response =authService.login(loginRequest);
		if(ObjectUtils.isEmpty(response))
		{
			log.info("Message : {}","Login Failed ");
			return CommonUtil.createErrorResponeMessage("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
		log.info("AuthController : login() : Execution End");
		return CommonUtil.createBuildRespone(response, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
