package com.suraj.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suraj.dto.PasswordChangeRequest;

@RequestMapping("/api/v1/user")
public interface UserEndPoint {
	
	@PostMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	
	@PostMapping("/chng-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws Exception;

}
