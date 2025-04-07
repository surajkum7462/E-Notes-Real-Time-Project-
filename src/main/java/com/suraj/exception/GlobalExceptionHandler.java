package com.suraj.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.suraj.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerException(Exception e) {
		log.error("GlobalExceptionHandler :: handlerException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handlerAccessDeniedException(AccessDeniedException e) {
		log.error("GlobalExceptionHandler :: handlerAccessDeniedException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.FORBIDDEN);
		
	}
	
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handlerIllegalArgumentException(IllegalArgumentException e) {
		log.error("GlobalExceptionHandler :: handlerIllegalArgumentException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e) {
		log.error("GlobalExceptionHandler :: handleNullPointerException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
		log.error("GlobalExceptionHandler :: handleBadCredentialsException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException : {}", e.getMessage());
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
		log.error("GlobalExceptionHandler :: handleValidationException : {}", e.getMessage());
		//return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorRespone(e.getErrors(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExistsDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistsDataException e) {
		log.error("GlobalExceptionHandler :: handleExistDataException : {}", e.getMessage());
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		log.error("GlobalExceptionHandler :: handleHttpMessageNotReadableException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		log.error("GlobalExceptionHandler :: handleFileNotFoundException : {}", e.getMessage());
		return CommonUtil.createErrorResponeMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e) {
		log.error("GlobalExceptionHandler :: handleSuccessException : {}", e.getMessage());
		//return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		return CommonUtil.createBuildResponeMessage(e.getMessage(), HttpStatus.OK);
	}
	
	
}
