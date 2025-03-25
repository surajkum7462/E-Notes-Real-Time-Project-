package com.suraj.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.entity.Category;
import com.suraj.repo.CategoryRepo;
import com.suraj.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Boolean saveCategory(Category category) {
		category.setIs_Deleted(false);
		category.setCreated_By(1);
		category.setCreated_On(new Date());
		
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> allCategory = categoryRepo.findAll();
		return allCategory;
	}
}
