package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Item;
import mks.myworkspace.english.toeic.repository.ItemRepository;

@Service
public interface ItemService {
	ItemRepository getRepo(); 
	
	List<Item> getItemByExamIDAndPartTitle(Long assessmentId, String title);
	
	Optional<Item> findById(Long id);

}
