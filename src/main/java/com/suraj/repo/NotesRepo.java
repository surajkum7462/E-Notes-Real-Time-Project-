package com.suraj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.Notes;

public interface NotesRepo extends JpaRepository<Notes, Integer>{

}
