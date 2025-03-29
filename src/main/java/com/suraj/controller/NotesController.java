package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.NotesDto;
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

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes() {
		List<NotesDto> allNotes = notesService.getAllNotes();
		if (CollectionUtils.isEmpty(allNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildRespone(allNotes, HttpStatus.OK);

	}

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
		
		FileDetails fileDetails=notesService.getFileDetails(id);

		byte[] data=notesService.downloadFile(fileDetails);
		
		HttpHeaders headers=new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		
		return ResponseEntity.ok().headers(headers).body(data);
		
	}


}
