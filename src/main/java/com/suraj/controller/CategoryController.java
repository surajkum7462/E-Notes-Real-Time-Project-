package com.suraj.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.CategoryDto;
import com.suraj.dto.CategoryResponse;

import com.suraj.endpoint.CategoryEndpoint;
import com.suraj.entity.Category;
import com.suraj.service.CategoryService;
import com.suraj.util.CommonUtil;

@RestController

public class CategoryController implements CategoryEndpoint{

	@Autowired
	private CategoryService categoryService;

	@Override
	public ResponseEntity<?> saveCategory(CategoryDto categoryDto) {
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return CommonUtil.createBuildResponeMessage("Saved Success", HttpStatus.CREATED);
			// return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
		} else {

			return CommonUtil.createErrorResponeMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);

			// return new ResponseEntity<>("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllCategory() {
//		String name=null;
//		name.toUpperCase();
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			
			return CommonUtil.createBuildRespone(allCategory, HttpStatus.OK);
			//return new ResponseEntity<>(allCategory, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if (CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildRespone(allCategory, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getCategoryDetailsById(Integer id) throws Exception {
		CategoryDto category = categoryService.getCategoryById(id);
		if (ObjectUtils.isEmpty(category)) {
			//return new ResponseEntity<>("Category Not Found ", HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponeMessage("Category Not Found", HttpStatus.NOT_FOUND);
		} else {
			//return new ResponseEntity<>(category, HttpStatus.OK);
			return CommonUtil.createBuildRespone(category, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<?> delete(Integer id) {
		Boolean b = categoryService.deleteCategory(id);
		if (b) {
			//return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
			return CommonUtil.createBuildResponeMessage("Deleted Successfully", HttpStatus.OK);
		} else {
			//return new ResponseEntity<>("Category is not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponeMessage("Category Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
