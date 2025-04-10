package com.suraj.endpoint;


import static com.suraj.util.Constatnts.DEFAULT_PAGE_NO;
import static com.suraj.util.Constatnts.DEFAULT_PAGE_SIZE;
import static com.suraj.util.Constatnts.ROLE_ADMIN;
import static com.suraj.util.Constatnts.ROLE_ADMIN_USER;
import static com.suraj.util.Constatnts.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.suraj.dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notes",description = "All the Notes APIs")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {
	
	@ApiResponses(value = { @ApiResponse(responseCode = "201",description = "Register Successfully"),
	@ApiResponse(responseCode = "500",description = "Internal Server Error"),
	@ApiResponse(responseCode = "400",description = "Bad Request")})
	@Operation(summary = "Save Notes",tags = {"Notes","User"},description = "User Save Notes")
	@PostMapping(value="/",consumes = "multipart/form-data")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> saveNotes(@RequestParam 
			@Parameter(description = "Json String Notes",required = true,
			content =  @Content(schema = @Schema(implementation = NotesRequest.class)))
			String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception ;
	
	
	@Operation(summary = "Download uploaded file",tags = {"Notes","User"},description = "Download File")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception ;
	
	
	@Operation(summary = "Get All Notes",tags = {"Notes"},description = "Get All Notes Admin")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes();
	
	
	@Operation(summary = "Get All Notes By User",tags = {"Notes","User"},description = "Get All Notes")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize);
	
	
	@Operation(summary = "Delete Notes",tags = {"Notes","User"},description = "Delete Notes By User")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	
	@Operation(summary = "Restore Delete Notes",tags = {"Notes","User"},description = "Restore Delete Notes By Recycle Bean")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	
	
	
	
	
	
	
	@Operation(summary = "Get Notes By Recycle Bean",tags = {"Notes","User"},description = "Get Notes By Recycle Bean")
	@GetMapping("/recycle")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> recycleBean() throws Exception;
	
	
	@Operation(summary = "Hard Delete Notes",tags = {"Notes","User"},description = "Hard Delete Notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception ;
	
	
	@Operation(summary = "Empty User Recycle Bean",tags = {"Notes","User"},description = "Empty User Recycle Bean")
	@DeleteMapping("/delete-recycle")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyUserRecycleBean() throws Exception ;
	
	
	
	
	@Operation(summary = "Favourite Notes",tags = {"Notes","User"},description = "User Add Favourite Notes")
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	@Operation(summary = "Unfavourite Notes",tags = {"Notes","User"},description = "User can do their unfavourite notes")
	@DeleteMapping("/un-fav/{favNoteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unfavouriteNote(@PathVariable Integer favNoteId) throws Exception;
	
	
	@Operation(summary = "Get Favourite Notes",tags = {"Notes","User"},description = "Get User Favourite Notes")
	@GetMapping("/fav-note")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserFavouriteNotes() throws Exception ;
	
	@Operation(summary = "Copy Notes",tags = {"Notes","User"},description = "Copy Notes")
	@GetMapping("/copy/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable Integer noteId) throws Exception ;
	
	
	@Operation(summary = "Search Notes",tags = {"Notes","User"},description = "User Search Notes")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotes(@RequestParam(name = "key" ,defaultValue = "") String key ,
			@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) ;

}
