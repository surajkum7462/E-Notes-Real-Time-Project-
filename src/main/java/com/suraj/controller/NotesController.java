package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.FavouriteNotesDto;
import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesResponse;
import com.suraj.endpoint.NotesEndpoint;
import com.suraj.entity.FileDetails;
import com.suraj.service.NotesService;
import com.suraj.util.CommonUtil;

@RestController

public class NotesController implements NotesEndpoint{

	@Autowired
	private NotesService notesService;

	@Override
	public ResponseEntity<?> saveNotes(String notes, MultipartFile file)
			throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return CommonUtil.createBuildResponeMessage("Notes Saved Successfully", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponeMessage("Notes Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> downloadFile(Integer id) throws Exception {

		FileDetails fileDetails = notesService.getFileDetails(id);

		byte[] data = notesService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);

	}

	@Override
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> getAllNotesByUser(Integer pageNo,
			 Integer pageSize) {

		Integer userId = CommonUtil.getLoggedInUser().getId();

		NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);
//		if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> deleteNotes(Integer id) throws Exception {

		notesService.softDeleteNotes(id);

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> restoreNotes(Integer id) throws Exception {

		notesService.restoreNotes(id);

		return CommonUtil.createBuildResponeMessage("Restore Success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> recycleBean() throws Exception {
	
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponeMessage("Not any Deleted Notes", HttpStatus.OK);
		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> hardDeleteNotes(Integer id) throws Exception {

		notesService.hardDeleteNotes(id);

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> emptyUserRecycleBean() throws Exception {
		
		notesService.emptyRecycleBean();

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> favouriteNote(Integer noteId) throws Exception {
		notesService.favouriteNotes(noteId);

		return CommonUtil.createBuildResponeMessage("Notes Added Favourite Successfullly", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> unfavouriteNote(Integer favNoteId) throws Exception {

		notesService.unFavouriteNotes(favNoteId);

		return CommonUtil.createBuildResponeMessage("Notes Unfavourite Successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception {
		List<FavouriteNotesDto> userfav = notesService.getUserFavouriteNotes();
		if (CollectionUtils.isEmpty(userfav)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildRespone(userfav, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> copyNotes(Integer noteId) throws Exception {
		Boolean copyNotes = notesService.copyNotes(noteId);
		if (!ObjectUtils.isEmpty(copyNotes)) {
			return CommonUtil.createBuildResponeMessage("Copied Successfullly", HttpStatus.OK);
		} else {
			return CommonUtil.createErrorResponeMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Override
	public ResponseEntity<?> searchNotes(String key ,
			Integer pageNo,
			Integer pageSize) {

		Integer userId = CommonUtil.getLoggedInUser().getId();

		NotesResponse notes = notesService.getNotesByUserSearch(pageNo, pageSize,key);

		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
