package com.suraj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
