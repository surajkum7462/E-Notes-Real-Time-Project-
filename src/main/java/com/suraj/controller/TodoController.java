package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.TodoDto;
import com.suraj.service.TodoService;
import com.suraj.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@PostMapping("/")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception {

		Boolean saveTodo = todoService.saveTodo(todoDto);
		if (saveTodo) {
			return CommonUtil.createBuildResponeMessage("Todo Saved Successfully", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponeMessage("Todo Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception {

		TodoDto todoById = todoService.getTodoById(id);

		return CommonUtil.createBuildRespone(todoById, HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public ResponseEntity<?> getAllTodoByUser(@PathVariable Integer id) throws Exception {

		List<TodoDto> todoUser = todoService.getTodoByUser();
        if(CollectionUtils.isEmpty(todoUser))
        {
        	return ResponseEntity.noContent().build();
        }
		return CommonUtil.createBuildRespone(todoUser, HttpStatus.CREATED);
	}

}
