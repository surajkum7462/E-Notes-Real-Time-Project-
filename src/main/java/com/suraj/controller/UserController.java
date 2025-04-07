package com.suraj.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.PasswordChangeRequest;
import com.suraj.dto.UserResponse;
import com.suraj.entity.User;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/profile")
	public ResponseEntity<?> getProfile()
	{
		log.info("UserController :: getProfile() : Start");
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		log.info("UserController :: getProfile() : End");
		return CommonUtil.createBuildRespone(userResponse, HttpStatus.OK);
	}
	
	
	

	@PostMapping("/chng-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws Exception
	{
		log.info("UserController :: changePassword() : Start");
		userService.changePassword(passwordChangeRequest);
		log.info("UserController : changePassword() : Execution End");
		return CommonUtil.createBuildRespone("Password Changed Successfully", HttpStatus.OK);
	}

}
