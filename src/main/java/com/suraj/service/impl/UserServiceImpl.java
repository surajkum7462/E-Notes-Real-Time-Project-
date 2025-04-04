package com.suraj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.suraj.dto.PasswordChangeRequest;
import com.suraj.entity.User;
import com.suraj.repo.UserRepo;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception {
		User loggedInUser = CommonUtil.getLoggedInUser();
		if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(),loggedInUser.getPassword()))
		{
			throw new IllegalAccessException("Old Password is Incorrect");
		}
		String encode = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
		loggedInUser.setPassword(encode);
		userRepo.save(loggedInUser);
		
	}

}
