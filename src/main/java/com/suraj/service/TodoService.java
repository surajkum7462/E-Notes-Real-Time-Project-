package com.suraj.service;

import java.util.List;

import com.suraj.dto.TodoDto;

public interface TodoService {
	
	public Boolean saveTodo(TodoDto todoDto) throws Exception;

	public TodoDto getTodoById(Integer id) throws Exception;
	
	public List<TodoDto> getTodoByUser();
	
	
}
