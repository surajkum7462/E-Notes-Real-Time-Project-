package com.suraj.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.suraj.handler.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildRespone(Object data, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().response(status).status("success").message("success")
				.data(data).build();
		return response.create();
	}

	public static ResponseEntity<?> createBuildResponeMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().response(status).status("success").message(message)
				.build();
		return response.create();
	}

	public static ResponseEntity<?> createErrorRespone(Object data, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().response(status).data(data).status("failed")
				.message("failed").build();
		return response.create();
	}

	public static ResponseEntity<?> createErrorResponeMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().response(status).status("failed").message(message).build();
		return response.create();
	}

}
