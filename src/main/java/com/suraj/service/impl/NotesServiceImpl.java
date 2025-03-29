package com.suraj.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.suraj.dto.NotesDto;
import com.suraj.dto.NotesDto.CategoryDto;
import com.suraj.entity.Notes;
import com.suraj.exception.ResourceNotFoundException;
import com.suraj.repo.CategoryRepo;
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

	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception {

		// category validation notes
		checkCatgeoryExist(notesDto.getCategory());

		Notes notes = mapper.map(notesDto, Notes.class);
		notesRepo.save(notes);
		if (!ObjectUtils.isEmpty(notes)) {
			return true;
		}
		return false;
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
