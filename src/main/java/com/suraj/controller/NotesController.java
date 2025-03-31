package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes, HttpStatus.OK);

	}

	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        
		Integer userId = 2;

		NotesResponse notes = notesService.getAllNotesByUser(userId,pageNo,pageSize);
//		if (CollectionUtils.isEmpty(notes)) {
//			return ResponseEntity.noContent().build();
//		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);

	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
	{
		
		notesService.softDeleteNotes(id);
		
		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}
	
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
	{
		
		notesService.restoreNotes(id);
		
		return CommonUtil.createBuildResponeMessage("Restore Success", HttpStatus.OK);
	}
	
	@GetMapping("/recycle/{id}")
	public ResponseEntity<?> recycleBean(@PathVariable Integer id) throws Exception
	{
		
		List<NotesDto> notes=notesService.getUserRecycleBinNotes(id);
		if(CollectionUtils.isEmpty(notes))
		{
			return CommonUtil.createBuildResponeMessage("Not any Deleted Notes", HttpStatus.OK);
		}
		return CommonUtil.createBuildRespone(notes, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception
	{
		
		notesService.hardDeleteNotes(id);
		
		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}
	
	
	

	@DeleteMapping("/delete-recycle")
	public ResponseEntity<?> emptyRecycleBean() throws Exception
	{
		Integer userId=2;
		notesService.emptyRecycleBean(userId);
		
		return CommonUtil.createBuildResponeMessage("Delete Success", HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
