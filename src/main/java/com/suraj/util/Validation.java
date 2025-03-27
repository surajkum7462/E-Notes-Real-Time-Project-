package com.suraj.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.CategoryDto;
import com.suraj.exception.ValidationException;

@Component
public class Validation {

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
				if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue() && categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
					error.put("isActive", "Invalid Value isActice Field");
				}

			}

		}
		
		if(!error.isEmpty())
		{
			throw new ValidationException(error);
		}
	}

}
