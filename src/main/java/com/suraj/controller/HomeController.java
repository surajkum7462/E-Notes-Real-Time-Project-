package com.suraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.PswdResetRequest;
import com.suraj.service.HomeService;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid , @RequestParam String code) throws Exception
	{
		Boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount)
		{
			return CommonUtil.createBuildResponeMessage("You have Successfully verified!now try to login", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponeMessage("Invalid Verification link", HttpStatus.BAD_REQUEST);
	}
	
	// 1.Send Email for verification
	
	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest request) throws Exception
	{
		userService.sendEmailPasswordReset(email,request);
		return CommonUtil.createBuildResponeMessage("Reset link is sent in your email", HttpStatus.OK);
	}
	
	// After Getting email with link and click on link then this url hit
	@GetMapping("/verify-pwsd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid , @RequestParam String code) throws Exception
	{
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createBuildResponeMessage("Verification success", HttpStatus.OK);
		
	}
	
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception
	{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponeMessage("Password reset success", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
