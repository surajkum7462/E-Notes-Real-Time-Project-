package com.suraj.service;

import java.util.List;

import com.suraj.dto.CategoryDto;
import com.suraj.dto.CategoryResponse;
import com.suraj.entity.Category;


public interface CategoryService {
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDto getCategoryById(Integer id);

	public Boolean deleteCategory(Integer id);
	
	

}
