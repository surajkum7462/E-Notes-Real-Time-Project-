package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.TodoDto;
import com.suraj.endpoint.TodoEndpoint;
import com.suraj.service.TodoService;
import com.suraj.util.CommonUtil;

@RestController

public class TodoController implements TodoEndpoint{

	@Autowired
	private TodoService todoService;

	@Override
	public ResponseEntity<?> saveTodo(TodoDto todoDto) throws Exception {

		Boolean saveTodo = todoService.saveTodo(todoDto);
		if (saveTodo) {
			return CommonUtil.createBuildResponeMessage("Todo Saved Successfully", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponeMessage("Todo Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getTodo(Integer id) throws Exception {

		TodoDto todoById = todoService.getTodoById(id);

		return CommonUtil.createBuildRespone(todoById, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getAllTodoByUser(Integer id) throws Exception {

		List<TodoDto> todoUser = todoService.getTodoByUser();
        if(CollectionUtils.isEmpty(todoUser))
        {
        	return ResponseEntity.noContent().build();
        }
		return CommonUtil.createBuildRespone(todoUser, HttpStatus.CREATED);
	}

}
