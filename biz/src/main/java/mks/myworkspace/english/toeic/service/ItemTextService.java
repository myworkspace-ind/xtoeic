package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.repository.ItemTextRepository;

@Service
public interface ItemTextService {
	ItemTextRepository getRepo();
	
	List<Object[]> getItemAndItemTextByExamIDAndPartTitle(Long assessmentId, String title);
}
