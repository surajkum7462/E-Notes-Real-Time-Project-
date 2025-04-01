package com.suraj.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.suraj.dto.CategoryDto;
import com.suraj.dto.TodoDto;
import com.suraj.dto.TodoDto.StatusDto;
import com.suraj.dto.UserDto;
import com.suraj.entity.Role;
import com.suraj.enums.TodoStatus;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.exception.ValidationException;
import com.suraj.repo.RoleRepo;

@Component
public class Validation {

	@Autowired
	private RoleRepo roleRepo;

	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object/JSON should not be null or empty");

		} else {

			// validation name field

			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "Name field is empty or null");
			} else {
				if (categoryDto.getName().length() < 10) {
					error.put("name", "name length min 10");
				}
				if (categoryDto.getName().length() > 100) {
					error.put("name", "name length max 100");
				}
			}

			// Validation Description

			if (ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "Description field is empty or null");
			}

			// validation isActive

			if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "Name field is empty or null");
			} else {
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
						&& categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "Invalid Value isActice Field");
				}

			}

		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}

	public void todoValidation(TodoDto todo) throws Exception {
		StatusDto reqStatus = todo.getStatus();

		Boolean statusFound = false;

		for (TodoStatus st : TodoStatus.values()) {
			if (st.getId().equals(reqStatus.getId())) {
				statusFound = true;
			}

		}
		if (!statusFound) {
			throw new ResourceNotFoundException("Invalid Status");
		}
	}

	public void userValidation(UserDto userDto)
	{
	
		
		if(!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("First Name is null");
		}
		
		if(!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("Last Name is null");
		}
		
		if(!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().trim().matches(Constatnts.EMAIL_REGEX)) {
			throw new IllegalArgumentException("Email is Invalid");
		}
		
		if(!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constatnts.MOBNO_REGEX)) {
			throw new IllegalArgumentException("MobNo is Invalid");
		}
		if (!StringUtils.hasText(userDto.getPassword())) {
		    throw new IllegalArgumentException("Password cannot be empty");
		} else if (!userDto.getPassword().matches(Constatnts.PASSWORD_REGEX)) {
		    throw new IllegalArgumentException("Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character.");
		}


		
		if(CollectionUtils.isEmpty(userDto.getRoles()))
		{
			throw new IllegalArgumentException("role is invalid");
		}
		else
		{
			List<Integer> roleIds = roleRepo.findAll().stream().map(r-> r.getId()).toList();
			
			List<Integer> invalidReqRoleids = userDto.getRoles().stream()
			.map(r->r.getId())
			.filter(roleId->!roleIds.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(invalidReqRoleids))
			{
				throw new IllegalArgumentException("role is invalid"+invalidReqRoleids);
			}
		}
		
		
	}

}
