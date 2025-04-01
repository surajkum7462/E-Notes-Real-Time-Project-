package com.suraj.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.UserDto;
import com.suraj.entity.Role;
import com.suraj.entity.User;
import com.suraj.repo.RoleRepo;
import com.suraj.repo.UserRepo;
import com.suraj.service.UserService;
import com.suraj.util.Validation;

import lombok.val;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	@Override
	public Boolean regitster(UserDto userDto) {
		validation.userValidation(userDto);
		
		User user = mapper.map(userDto, User.class);
		
		setRole(userDto,user);
		
		
		
		User save = userRepo.save(user);
		if(!ObjectUtils.isEmpty(save))
		{
			return true;
		}
		
		
		
		
		return false;
	}

	private void setRole(UserDto userDto,User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);
	}

}
