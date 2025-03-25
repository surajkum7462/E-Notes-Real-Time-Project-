package com.suraj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.dto.CategoryResponse;
import com.suraj.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

	List<Category> findByIsActiveTrue();

	
}
