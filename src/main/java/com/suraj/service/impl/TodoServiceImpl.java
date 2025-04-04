package com.suraj.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.TodoDto;
import com.suraj.dto.TodoDto.StatusDto;
import com.suraj.entity.Todo;
import com.suraj.enums.TodoStatus;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.TodoRepo;
import com.suraj.service.TodoService;
import com.suraj.util.CommonUtil;
import com.suraj.util.Validation;

@Service
public class TodoServiceImpl implements TodoService{
	
	
	
	@Autowired
	private TodoRepo todoRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	

	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		 
		
		validation.todoValidation(todoDto);
		
		
		Todo todo = mapper.map(todoDto, Todo.class);
		
		todo.setStatusId(todoDto.getStatus().getId());
		
		
		Todo save = todoRepo.save(todo);
		if(!ObjectUtils.isEmpty(save))
		{
			return true;
		}
		return false;
	}

	

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
      Todo todo = todoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Todo Id is Invalid"));
      
		TodoDto todoDto = mapper.map(todo, TodoDto.class);
		setStatus(todoDto,todo);
		return todoDto;
	}
	
	private void setStatus(TodoDto todoDto, Todo todo) {
		for(TodoStatus st : TodoStatus.values())
		{
			if(st.getId().equals(todo.getStatusId()))
			{
				StatusDto statusDto=StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						
						.build();
				todoDto.setStatus(statusDto);
			}
		}
		
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<Todo> todos=todoRepo.findByCreatedBy(userId);
		List<TodoDto> todoList = todos.stream().map(td->mapper.map(td, TodoDto.class)).toList();
		
		return todoList;
	}

}
