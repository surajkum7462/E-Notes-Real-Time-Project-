package com.suraj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suraj.entity.AccountStatus;
import com.suraj.entity.User;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.exception.SuccessException;
import com.suraj.repo.UserRepo;
import com.suraj.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRepo userRepo;
	
	
	

	@Override
	public Boolean verifyAccount(Integer uid, String verificationCode) throws Exception {
		
		log.info("HomeServiceImpl : verifyAccount() : Start");
		User user = userRepo.findById(uid).orElseThrow(()->new ResourceNotFoundException("User ID is invalid"));
		
		if(user.getStatus().getVerificationCode()==null)
		{
			log.info("message: Your Account is Already verified");
			throw new SuccessException("Your Account is Already verified");
		}
		
		
		
		
		
		if(user.getStatus().getVerificationCode().equals(verificationCode))
		{
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepo.save(user);
			log.info("message:  Account is  verified successfully");
			return true;
		}
		
		log.info("HomeServiceImpl : verifyAccount() : End");
		
		return false;
	}

}
