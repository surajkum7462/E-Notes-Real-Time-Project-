package com.suraj.service;

import java.util.List;

import com.suraj.dto.CategoryDto;
import com.suraj.dto.CategoryResponse;
import com.suraj.entity.Category;
import com.suraj.exception.ResourceNotFoundException;


public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDto getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategory(Integer id);
	
	

}
