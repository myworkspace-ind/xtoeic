package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.ItemText;
import mks.myworkspace.english.toeic.repository.ItemTextRepository;
import mks.myworkspace.english.toeic.service.ItemTextService;

@Service
public class ItemTextServiceImpl implements ItemTextService{
	
	@Autowired
	private ItemTextRepository repo;
	
	@Override
	public ItemTextRepository getRepo() {
		return repo;
	} 
 
	@Override
	public List<ItemText> getItemTextByExamIDAndPartTitle(Long assessmentId, String title) {
	    return repo.getItemTextByExamIDAndPartTitle(assessmentId, title);
	}
	
	@Override
	public Optional<ItemText> findById(Long id) {
        return repo.findById(id);
    }

	
	
	
}
