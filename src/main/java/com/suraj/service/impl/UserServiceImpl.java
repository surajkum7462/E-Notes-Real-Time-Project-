package com.suraj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.suraj.dto.PasswordChangeRequest;
import com.suraj.entity.User;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception {
		
			User loggedInUser = CommonUtil.getLoggedInUser();
			if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(),loggedInUser.getPassword()))
			{
				throw new IllegalAccessException("Old Password is Incorrect");
			}

			
		}
		
	}

	
	

