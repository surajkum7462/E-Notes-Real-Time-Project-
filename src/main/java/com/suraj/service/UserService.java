package com.suraj.service;

import com.suraj.dto.PasswordChangeRequest;
import com.suraj.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception;

	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

	public void verifyPswdResetLink(Integer uid, String code) throws Exception;

	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception;

}
