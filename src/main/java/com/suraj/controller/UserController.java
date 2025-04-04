package com.suraj.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.UserResponse;
import com.suraj.entity.User;
import com.suraj.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	
	@Autowired
	private ModelMapper mapper;
	
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile()
	{
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildRespone(userResponse, HttpStatus.OK);
	}

}
