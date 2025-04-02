package com.suraj.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.suraj.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

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

	

	public static String getContentType(String originalFileName) {
	    String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();

	    switch (extension) {
	        case "pdf": return "application/pdf";
	        case "txt": return "text/plain";
	        case "html": case "htm": return "text/html";
	        case "css": return "text/css";
	        case "js": return "application/javascript";
	        case "json": return "application/json";
	        case "xml": return "application/xml";
	        case "csv": return "text/csv";
	        case "zip": return "application/zip";
	        case "gz": return "application/gzip";
	        case "rar": return "application/vnd.rar";
	        case "tar": return "application/x-tar";
	        case "7z": return "application/x-7z-compressed";

	        // Image formats
	        case "jpg": case "jpeg": return "image/jpeg";
	        case "png": return "image/png";
	        case "gif": return "image/gif";
	        case "bmp": return "image/bmp";
	        case "webp": return "image/webp";
	        case "svg": return "image/svg+xml";

	        // Audio formats
	        case "mp3": return "audio/mpeg";
	        case "wav": return "audio/wav";
	        case "ogg": return "audio/ogg";
	        case "m4a": return "audio/mp4";

	        // Video formats
	        case "mp4": return "video/mp4";
	        case "avi": return "video/x-msvideo";
	        case "mov": return "video/quicktime";
	        case "wmv": return "video/x-ms-wmv";
	        case "flv": return "video/x-flv";
	        case "webm": return "video/webm";
	        case "mkv": return "video/x-matroska";

	        // Microsoft Office formats
	        case "doc": return "application/msword";
	        case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	        case "xls": return "application/vnd.ms-excel";
	        case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	        case "ppt": return "application/vnd.ms-powerpoint";
	        case "pptx": return "application/vnd.openxmlformats-officedocument.presentationml.presentation";

	        // Other document formats
	        case "rtf": return "application/rtf";
	        case "odt": return "application/vnd.oasis.opendocument.text";
	        case "ods": return "application/vnd.oasis.opendocument.spreadsheet";
	        case "odp": return "application/vnd.oasis.opendocument.presentation";

	        default: return "application/octet-stream"; // Generic binary stream for unknown files
	    }
	}

	public static String getUrl(HttpServletRequest request) {
		String apiUrl = request.getRequestURL().toString();
		// http://localhost:8080/api/v1/auth
		String servletPath = request.getServletPath();
		// /api/v1/auth
		apiUrl=apiUrl.replace(servletPath, "");
		return apiUrl;
	}


}
