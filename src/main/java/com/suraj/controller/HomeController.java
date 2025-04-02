package com.suraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.service.HomeService;
import com.suraj.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	
	@Autowired
	private HomeService homeService;
	
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

}
