package com.suraj.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.suraj.entity.Notes;
import com.suraj.repo.NotesRepo;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepo notesRepo;
	
	@Scheduled(cron = "0 0 0 * * ?")
	//@Scheduled(cron = "* * * ? * *")
	public void deleteNotesSchedular()
	{
		 // Today 31-March -24March = 7 Days
		System.out.println("i");
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
		
		List<Notes> deletedNotes=notesRepo.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDate);
		
		notesRepo.deleteAll(deletedNotes);
		
		
	}

}
