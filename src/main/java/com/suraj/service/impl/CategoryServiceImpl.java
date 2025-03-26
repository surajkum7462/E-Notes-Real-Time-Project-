package com.suraj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.CategoryDto;
import com.suraj.dto.CategoryResponse;
import com.suraj.entity.Category;
import com.suraj.repo.CategoryRepo;
import com.suraj.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
//		category.setIs_Deleted(false);
//		category.setCreated_By(1);
//		category.setCreated_On(new Date());
		
//		Category saveCategory = categoryRepo.save(category);
//		if (ObjectUtils.isEmpty(saveCategory)) {
//			return false;
//		}
		
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIs_Active(categoryDto.getIs_Active());
//		
		
		Category category = mapper.map(categoryDto, Category.class);
	    if(ObjectUtils.isEmpty(category.getId()))
	    {
	    	category.setIsDeleted(false);
		    category.setCreatedBy(1);
			category.setCreatedOn(new Date());
	    }
	    else
	    {
	    	updateCategory(category);
	    }
		
		
		
		
		
		
		
		Category saveCategory = categoryRepo.save(category);
		if (ObjectUtils.isEmpty(saveCategory)) {
			return false;
	}			
		return true;
	}

	private void updateCategory(Category category) {
       Optional<Category> findById = categoryRepo.findById(category.getId());		
       if(findById.isPresent())
       {
    	   Category existCategory = findById.get();
    	   category.setCreatedBy(existCategory.getCreatedBy());
    	   category.setCreatedOn(existCategory.getCreatedOn());
    		category.setIsDeleted(existCategory.getIsDeleted());
		    category.setCreatedBy(existCategory.getCreatedBy());
		    
		    category.setUpdatedBy(1);
		    category.setUpdatedOn(new Date());
       }
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategory = categoryRepo.findByIsDeletedFalse();
		List<CategoryDto> list = allCategory.stream().map(cat->mapper.map(cat, CategoryDto.class)).toList();
		return list;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories=categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> list = categories.stream().map(cate->mapper.map(cate, CategoryResponse.class)).toList();
		return list;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Optional<Category> findByCategory = categoryRepo.findByIdAndIsDeletedFalse(id);
		if(findByCategory.isPresent())
		{
			Category category = findByCategory.get();
			return mapper.map(category, CategoryDto.class);
		}
		return null;
	}

	@Override
	public Boolean deleteCategory(Integer id) {
		Optional<Category> cat = categoryRepo.findById(id);
		
		if(cat.isPresent())
		{
			Category category = cat.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}
}
