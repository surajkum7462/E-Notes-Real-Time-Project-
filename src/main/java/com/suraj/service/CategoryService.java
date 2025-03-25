package com.suraj.service;

import java.util.List;

import com.suraj.entity.Category;


public interface CategoryService {
	
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();

}
