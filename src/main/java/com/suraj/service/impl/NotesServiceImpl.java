package com.suraj.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suraj.dto.FavouriteNotesDto;
import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesDto.CategoryDto;
import com.suraj.dto.NotesDto.FilesDto;
import com.suraj.dto.NotesResponse;
import com.suraj.entity.FavouriteNotes;
import com.suraj.entity.FileDetails;
import com.suraj.entity.Notes;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.CategoryRepo;
import com.suraj.repo.FIleDetailsRepo;
import com.suraj.repo.FavouriteNotesRepo;
import com.suraj.repo.NotesRepo;
import com.suraj.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepo notesRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private FavouriteNotesRepo favouriteNotesRepo;

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	private FIleDetailsRepo fIleDetailsRepo;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		// Convert String into Object like Notes data

		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);

		// Update notes if id is given
		if (!ObjectUtils.isEmpty(notesDto.getId())) {
			updateNotes(notesDto, file);
		}

		// category validation notes
		checkCatgeoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		} else {
			if (ObjectUtils.isEmpty(notesDto.getId())) {
				notesMap.setFileDetails(null);
			}

		}

		Notes save = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {

		Notes existNotes = notesRepo.findById(notesDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid id "));

		// Set the existing file
		if (ObjectUtils.isEmpty(file)) {
			if (existNotes.getFileDetails() != null) {
				notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(), FilesDto.class));
			} else {
				notesDto.setFileDetails(null); // Prevents null mapping issue
			}
		}

	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpeg", "png", "docx");

			if (!extensionAllow.contains(extension)) {
				throw new IllegalArgumentException("Invalid file Format !Upload only .pdf,.jpeg,.png");
			}

			String rndString = UUID.randomUUID().toString();

			String uploadFileName = rndString + "." + extension;

			File savefile = new File(uploadPath);

			if (!savefile.exists()) {
				savefile.mkdir();
			}
			// Path set:enotesapiservice/notes/java.pdf
			String storePath = uploadPath.concat(uploadFileName);

			// Upload File
			long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
			if (upload != 0) {
				FileDetails fileDtls = new FileDetails();
				fileDtls.setOriginalFileName(originalFileName);
				fileDtls.setDisplayFileName(getDisplayName(originalFileName));

				fileDtls.setUploadFileName(uploadFileName);
				fileDtls.setFileSize(file.getSize());
				fileDtls.setPath(storePath);
				FileDetails saveFileDtls = fIleDetailsRepo.save(fileDtls);

				return saveFileDtls;
			}
		}

		return null;
	}

	private String getDisplayName(String originalFileName) {
		// origi-javaprogrammingtuto.pdf

		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		// get-> origi-javaprogrammingtuto

		if (fileName.length() > 8) {
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;

		// origi-ja.pdf
		return fileName;
	}

	private void checkCatgeoryExist(CategoryDto categoryDto) throws Exception {
		categoryRepo.findById(categoryDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Category id is invalid"));

	}

	@Override
	public List<NotesDto> getAllNotes() {
		List<Notes> all = notesRepo.findAll();
		List<NotesDto> list = all.stream().map(note -> mapper.map(note, NotesDto.class)).toList();
		return list;
	}

	@Override
	public byte[] downloadFile(FileDetails fileDtls) throws Exception {

		InputStream io = new FileInputStream(fileDtls.getPath());

		byte[] byteData = StreamUtils.copyToByteArray(io);

		return byteData;
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		FileDetails fileDtls = fIleDetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File is not available"));
		return fileDtls;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {

		// 10 data = 5,5-> 2 Pages
		PageRequest pageble = PageRequest.of(pageNo, pageSize);

		Page<Notes> pageNotes = notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageble);

		List<NotesDto> notesDto = pageNotes.get().map(n -> mapper.map(n, NotesDto.class)).toList();

		NotesResponse notesRes = NotesResponse.builder().notes(notesDto).pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize()).totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages()).isFirst(pageNotes.isFirst()).isLast(pageNotes.isLast())

				.build();

		return notesRes;
	}

	@Override
	public void softDeleteNotes(Integer id) throws Exception {
		Notes note = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id"));
		note.setIsDeleted(true);
		note.setDeletedOn(LocalDateTime.now());
		notesRepo.save(note);

	}

	@Override
	public void restoreNotes(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Notes note = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid id"));
		note.setIsDeleted(false);
		note.setDeletedOn(null);
		notesRepo.save(note);

	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer id) {

		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(id);

		List<NotesDto> notesDto = recycleNotes.stream().map(note -> mapper.map(note, NotesDto.class)).toList();

		return notesDto;
	}

	@Override
	public void hardDeleteNotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes Not Found"));

		if (notes.getIsDeleted()) {
			notesRepo.delete(notes);
		} else {
			throw new IllegalArgumentException("Sorry You can't delete Directly");
		}

	}

	@Override
	public void emptyRecycleBean(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		if (!CollectionUtils.isEmpty(recycleNotes)) {
			notesRepo.deleteAll(recycleNotes);
		}
	}

	@Override
	public void favouriteNotes(Integer notesId) throws Exception {
		Integer userId = 2;
		Notes notes = notesRepo.findById(notesId)
				.orElseThrow(() -> new ResourceNotFoundException("Notes Not FOund !Invalid Id"));

		FavouriteNotes favouriteNotes = FavouriteNotes.builder()

				.notes(notes).userId(userId).build();
		favouriteNotesRepo.save(favouriteNotes);

	}

	@Override
	public void unFavouriteNotes(Integer favouriteNotesId) throws Exception {
		FavouriteNotes notes = favouriteNotesRepo.findById(favouriteNotesId)
				.orElseThrow(() -> new ResourceNotFoundException("Notes is not added in favourite !Invalid Id"));
		favouriteNotesRepo.delete(notes);
	}

	@Override
	public List<FavouriteNotesDto> getUserFavouriteNotes() throws Exception {
		Integer userId = 2;

		List<FavouriteNotes> favNotes = favouriteNotesRepo.findByUserId(userId);

		List<FavouriteNotesDto> list = favNotes.stream().map(fn -> mapper.map(fn, FavouriteNotesDto.class)).toList();

		return list;
	}

	@Override
	public Boolean copyNotes(Integer noteId) throws Exception {
		Notes notes = notesRepo.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Notes Not FOund !Invalid Id"));

		Notes copyNotes = Notes.builder()

				.title(notes.getTitle()).description(notes.getDescription()).category(notes.getCategory())
				.isDeleted(false).fileDetails(notes.getFileDetails()).build();

		// TODO : Need to Check User Validation

		Notes save = notesRepo.save(copyNotes);
		if (!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;

	}

}
