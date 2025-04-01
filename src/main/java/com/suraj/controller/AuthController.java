package com.suraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.UserDto;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) throws Exception {
		Boolean register = userService.regitster(userDto);
		if (register) {
			return CommonUtil.createBuildResponeMessage("Register Successfull !Please check your email for verification", HttpStatus.OK);
		} else {
			return CommonUtil.createErrorResponeMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
