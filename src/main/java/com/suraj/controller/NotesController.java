package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.FavouriteNotesDto;
import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesResponse;
import com.suraj.entity.FileDetails;
import com.suraj.service.NotesService;
import com.suraj.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception {
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return CommonUtil.createBuildResponeMessage("Notes Saved Successfully", HttpStatus.CREATED);
		} else {
			return CommonUtil.createErrorResponeMessage("Notes Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {

		FileDetails fileDetails = notesService.getFileDetails(id);

		byte[] data = notesService.downloadFile(fileDetails);

		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);

	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes, HttpStatus.OK);

	}

	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		Integer userId = CommonUtil.getLoggedInUser().getId();

		NotesResponse notes = notesService.getAllNotesByUser(pageNo, pageSize);
//		if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);

	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {

		notesService.softDeleteNotes(id);

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {

		notesService.restoreNotes(id);

		return CommonUtil.createBuildResponeMessage("Restore Success", HttpStatus.OK);
	}

	@GetMapping("/recycle")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> recycleBean() throws Exception {
	
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if (CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createBuildResponeMessage("Not any Deleted Notes", HttpStatus.OK);
		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {

		notesService.hardDeleteNotes(id);

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@DeleteMapping("/delete-recycle")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> emptyUserRecycleBean() throws Exception {
		
		notesService.emptyRecycleBean();

		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}

	@GetMapping("/fav/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception {
		notesService.favouriteNotes(noteId);

		return CommonUtil.createBuildResponeMessage("Notes Added Favourite Successfullly", HttpStatus.OK);
	}

	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unfavouriteNote(@PathVariable Integer favNoteId) throws Exception {

		notesService.unFavouriteNotes(favNoteId);

		return CommonUtil.createBuildResponeMessage("Notes Unfavourite Successfully", HttpStatus.OK);
	}

	@GetMapping("/fav-note")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception {
		List<FavouriteNotesDto> userfav = notesService.getUserFavouriteNotes();
		if (CollectionUtils.isEmpty(userfav)) {
			return ResponseEntity.noContent().build();
		}

		return CommonUtil.createBuildRespone(userfav, HttpStatus.OK);
	}

	@GetMapping("/copy/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception {
		Boolean copyNotes = notesService.copyNotes(noteId);
		if (!ObjectUtils.isEmpty(copyNotes)) {
			return CommonUtil.createBuildResponeMessage("Copied Successfullly", HttpStatus.OK);
		} else {
			return CommonUtil.createErrorResponeMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
