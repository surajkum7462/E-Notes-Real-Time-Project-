package com.suraj.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suraj.entity.FavouriteNotes;

public interface FavouriteNotesRepo extends JpaRepository<FavouriteNotes, Integer>{

	List<FavouriteNotes> findByUserId(Integer userId);

}
