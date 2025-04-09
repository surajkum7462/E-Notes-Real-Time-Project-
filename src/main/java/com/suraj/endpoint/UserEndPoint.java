package com.suraj.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suraj.dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User",description = "Authentication User Operation APIs")
@RequestMapping("/api/v1/user")
public interface UserEndPoint {
	
	@Operation(summary = "Get User Profile",tags = {"User"},description = "Get User Profile")
	@PostMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@Operation(summary = "Password Change",tags = {"User"},description = "User can do their password change")
	@PostMapping("/chng-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws Exception;

}
