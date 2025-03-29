package com.suraj.service;

import java.util.List;

import com.suraj.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
