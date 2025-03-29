package com.suraj.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
