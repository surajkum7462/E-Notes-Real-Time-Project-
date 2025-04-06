package com.suraj.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.FavouriteNotesDto;
import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesResponse;
import com.suraj.entity.FavouriteNotes;
import com.suraj.entity.FileDetails;

public interface NotesService {
	
	public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);
	
	public NotesResponse getNotesByUserSearch(Integer pageNo, Integer pageSize,String keyword);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception;

	public List<NotesDto> getUserRecycleBinNotes();

	public void hardDeleteNotes(Integer id) throws Exception;

	public void emptyRecycleBean();
	
	public void favouriteNotes(Integer notesId) throws Exception;
	
	public void unFavouriteNotes(Integer favouriteNotesId) throws Exception;
	
	public List<FavouriteNotesDto>  getUserFavouriteNotes() throws Exception;

	public Boolean copyNotes(Integer noteId) throws Exception;

}
