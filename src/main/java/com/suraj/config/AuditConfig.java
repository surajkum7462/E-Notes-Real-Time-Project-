package com.suraj.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.suraj.entity.User;
import com.suraj.util.CommonUtil;

public class AuditConfig implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor() {
		// TODO Auto-generated method stub
		User loggedInUser = CommonUtil.getLoggedInUser();
		return  Optional.of( loggedInUser.getId());
	}

}
