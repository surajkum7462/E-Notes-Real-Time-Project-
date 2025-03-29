package com.suraj.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesDto.CategoryDto;
import com.suraj.entity.FileDetails;
import com.suraj.entity.Notes;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.CategoryRepo;
import com.suraj.repo.FIleDetailsRepo;
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

	@Value("${file.upload.path}")
	private String uploadPath;

	@Autowired
	private FIleDetailsRepo fIleDetailsRepo;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

		// Convert String into Object like Notes data

		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);

		// category validation notes
		checkCatgeoryExist(notesDto.getCategory());

		Notes notesMap = mapper.map(notesDto, Notes.class);

		FileDetails fileDtls = saveFileDetails(file);

		if (!ObjectUtils.isEmpty(fileDtls)) {
			notesMap.setFileDetails(fileDtls);
		} else {
			notesMap.setFileDetails(null);
		}

		Notes save = notesRepo.save(notesMap);
		if (!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {

		if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension(originalFileName);

			List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpeg", "png");

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

}
