package com.suraj.service;

import com.suraj.dto.PasswordChangeRequest;

public interface UserService {
	
	public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception;

}
