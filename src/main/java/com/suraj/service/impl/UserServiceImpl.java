package com.suraj.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.config.security.CustomUserDetails;
import com.suraj.dto.EmailRequest;
import com.suraj.dto.LoginRequest;
import com.suraj.dto.LoginResponse;
import com.suraj.dto.PasswordChangeRequest;
import com.suraj.dto.UserRequest;
import com.suraj.entity.AccountStatus;
import com.suraj.entity.Role;
import com.suraj.entity.User;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.RoleRepo;
import com.suraj.repo.UserRepo;
import com.suraj.service.JWTService;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;
import com.suraj.util.Validation;

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

	
	

