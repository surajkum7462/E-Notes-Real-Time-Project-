package com.suraj.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.suraj.dto.EmailRequest;
import com.suraj.dto.PasswordChangeRequest;
import com.suraj.dto.PswdResetRequest;
import com.suraj.entity.User;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.UserRepo;
import com.suraj.service.UserService;
import com.suraj.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailService emailService;

	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) throws Exception {

		User loggedInUser = CommonUtil.getLoggedInUser();
		if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
			throw new IllegalAccessException("Old Password is Incorrect");
		}

	}

	@Override
	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
		User user = userRepo.findByEmail(email);
		if (ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid Email");
		}
		// Generate password reset token
		String token = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(token);
		User updateUser = userRepo.save(user);

		String url = CommonUtil.getUrl(request);
		sendPasswordResetEmail(updateUser, url);

	}

	private void sendPasswordResetEmail(User user, String baseUrl) throws Exception {

		String resetLink = baseUrl + "/api/v1/home/verify-pwsd-link?uid=" + user.getId() + "&code="
				+ user.getStatus().getPasswordResetToken();

		String message = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; text-align: center;'>"
				+ "<h2 style='color: #2E86C1; margin-bottom: 10px;'>Hello <span style='color: #28a745;'>[[username]]</span>,</h2>"
				+ "<p style='font-size: 16px; color: #555; line-height: 1.5;'>You received a request to reset your password for your <b>E-Notes</b> account.</p>"
				+ "<p style='font-size: 16px; color: #555; line-height: 1.5;'>Click the button below to set a new password:</p>"
				+ "<a href='[[resetLink]]' style='display: inline-block; background: linear-gradient(90deg, #28a745, #218838); color: #fff; font-size: 16px; font-weight: bold; padding: 12px 24px; text-decoration: none; border-radius: 6px; box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); margin: 20px auto; display: block; width: max-content;'>🔒 Reset My Password</a>"
				+ "<p style='font-size: 14px; color: #777; margin-top: 20px;'>This link will expire in <b>24 hours</b>. If you did not request a password reset, please ignore this email or contact support.</p>"
				+ "<hr style='border: 0; height: 1px; background: #ddd; margin: 20px 0;'>"
				+ "<p style='font-size: 14px; color: #555;'>Need help? Contact our <a href='mailto:support@e-notes.com' style='color: #2E86C1; text-decoration: none;'>support team</a>.</p>"
				+ "<p style='font-size: 14px; color: #555; font-weight: bold;'>Best Regards,<br><span style='color: #2E86C1;'>E-Notes Team</span></p>"
				+ "<p style='font-size: 12px; color: #aaa;'>© 2025 E-Notes. All rights reserved.</p>" + "</div>";

		message = message.replace("[[username]]", user.getFirstName());
		message = message.replace("[[resetLink]]", resetLink);

		EmailRequest emailReq = EmailRequest.builder().to(user.getEmail()).title("Reset Your Password - E-Notes")
				.subject("E-Notes Password Reset Request").message(message).build();

		emailService.send(emailReq);
	}

	@Override
	public void verifyPswdResetLink(Integer uid, String code) throws Exception {
		User user = userRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid User"));

		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);
		

	}

	private void verifyPasswordResetToken(String existToken, String reqToken) {

		// Request Token not null
		if (StringUtils.hasText(reqToken)) {
			
			// If password already reset
			if (!StringUtils.hasText(existToken)) {
				throw new IllegalArgumentException("Already Password reset");
			}

			// user request token changes
			if (!existToken.equals(reqToken)) {
				throw new IllegalArgumentException("Invalid Url");
			}
		} else {
			throw new IllegalArgumentException("Invalid Token");
		}

	}

	@Override
	public void resetPassword(PswdResetRequest pswdResetRequest) throws Exception {
		User user = userRepo.findById(pswdResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("Invalid User Id"));
		String encode = passwordEncoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encode);
		user.getStatus().setPasswordResetToken(null);
		userRepo.save(user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
