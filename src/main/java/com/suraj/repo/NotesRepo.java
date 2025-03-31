package com.suraj.repo;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.Notes;

public interface NotesRepo extends JpaRepository<Notes, Integer>{

	Page<Notes> findByCreatedBy(Integer userId, PageRequest pageble);


	List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);


	Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, PageRequest pageble);


	List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, LocalDateTime cutOffDate);

}
