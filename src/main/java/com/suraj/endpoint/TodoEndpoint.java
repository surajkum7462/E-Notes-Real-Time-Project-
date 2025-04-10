package com.suraj.endpoint;

import static com.suraj.util.Constatnts.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suraj.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Todo",description = "All the  Todo's APIs")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {
	
	@Operation(summary = "Save Todo",tags = {"Todo"},description = "Save Todo")
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws Exception;

	
	
	@Operation(summary = "Get Todo",tags = {"Todo"},description = "Get Todo")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodo(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Get All Todo",tags = {"Todo"},description = "Get All Todo By User")
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser(@PathVariable Integer id) throws Exception ;
}
