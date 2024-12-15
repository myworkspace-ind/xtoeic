package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.ItemText;
import mks.myworkspace.english.toeic.repository.ItemTextRepository;

@Service
public interface ItemTextService {
	ItemTextRepository getRepo();
	
	List<ItemText> getItemTextByExamIDAndPartTitle(Long assessmentId, String title);
	
	Optional<ItemText> findById(Long id);
}
