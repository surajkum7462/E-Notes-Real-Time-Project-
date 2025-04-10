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
import com.suraj.dto.UserRequest;
import com.suraj.dto.UserResponse;
import com.suraj.entity.AccountStatus;
import com.suraj.entity.Role;
import com.suraj.entity.User;
import com.suraj.repo.RoleRepo;
import com.suraj.repo.UserRepo;
import com.suraj.service.AuthService;
import com.suraj.service.JWTService;
import com.suraj.util.Validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Validation validation;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;

	@Override
	public Boolean regitster(UserRequest userDto,String url) throws Exception {
		log.info("AuthServiceImpl : regitster() : Execution Start");
		validation.userValidation(userDto);

		User user = mapper.map(userDto, User.class);

		setRole(userDto, user);

		AccountStatus status = AccountStatus.builder().isActive(false).verificationCode(UUID.randomUUID().toString())
				.build();

		user.setStatus(status);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
		User save = userRepo.save(user);
		if (ObjectUtils.isEmpty(save)) {
			log.info("Message : {}","User Not Saved ");
			return false;
			
		}
		log.info("Message : {}","User Register Success");
		// send email
		emailSendForRegister(save,url);
		log.info("Message : {}","email send success ");
		log.info("AuthServiceImpl : regitster() : Execution End");
		return true;
	}

	private void emailSendForRegister(User save, String url) throws Exception {
		String message = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; text-align: center;'>"
				+ "<h2 style='color: #2E86C1; margin-bottom: 10px;'>Welcome to <span style='color: #28a745;'>E-Notes</span>, "
				 + "[[username]]!</h2>"
				+ "<p style='font-size: 16px; color: #555; line-height: 1.5;'>We're excited to have you on board. Your account has been successfully registered. 🎉</p>"
				+ "<p style='font-size: 16px; color: #555; line-height: 1.5;'>Please verify your email to start using E-Notes:</p>"
				+ "<a href='[[url]]' style='display: inline-block; background: linear-gradient(90deg, #28a745, #218838); color: #fff; font-size: 16px; font-weight: bold; padding: 12px 24px; text-decoration: none; border-radius: 6px; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); margin: 20px auto; display: block; width: max-content;'>✅ Verify Your Account</a>"
				+ "<p style='font-size: 14px; color: #777; margin-top: 20px;'>If you did not sign up for this account, you can safely ignore this email.</p>"
				+ "<hr style='border: 0; height: 1px; background: #ddd; margin: 20px 0;'>"
				+ "<p style='font-size: 14px; color: #555;'>Need help? Contact our support team anytime.</p>"
				+ "<p style='font-size: 14px; color: #555; font-weight: bold;'>Thanks,<br><span style='color: #2E86C1;'>E-Notes Team</span></p>"
				+ "<p style='font-size: 12px; color: #aaa;'>© 2025 E-Notes. All rights reserved.</p>" + "</div>";

		 message = message.replace("[[username]]", save.getFirstName());
		
		 message =  message.replace("[[url]]",url+"/api/v1/home/verify?uid="+save.getId()+"&code="+save.getStatus().getVerificationCode());
		
		
		
		
		
		EmailRequest emailReq = EmailRequest.builder().to(save.getEmail()).title("Account Registration Confirmation")
				.subject("🎉 Welcome to E-Notes! Your Account is Ready").message(message).build();

		emailService.send(emailReq);
	}

	private void setRole(UserRequest userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		log.info("AuthServiceImpl : login() : Execution Start");
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated())
		{
		     CustomUserDetails customUserDetails =(CustomUserDetails)authenticate.getPrincipal();
			
		     // For generating JWT token i create a class in service
		     
			String token=jwtService.generateToken(customUserDetails.getUser());
			
					
			LoginResponse loginResponse = LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token)
				    .build();
			log.info("AuthServiceImpl : regitster() : Execution End");
					return loginResponse;
		}
		log.info("Message : {}","Login Failed ");
		return null;
	}
}
