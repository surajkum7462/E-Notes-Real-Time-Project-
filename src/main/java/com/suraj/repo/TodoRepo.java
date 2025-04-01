package com.suraj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.Todo;

public interface TodoRepo extends JpaRepository<Todo, Integer>{

	List<Todo> findByCreatedBy(Integer userId);

}
