package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Item;
import mks.myworkspace.english.toeic.repository.ItemRepository;
import mks.myworkspace.english.toeic.service.ItemService;

@Service
public class ItemImpl implements ItemService{
	@Autowired
	private ItemRepository repo;
	
	@Override
	public ItemRepository getRepo() {
		return repo;
	} 
 
	@Override
	public List<Item> getItemByExamIDAndPartTitle(Long assessmentId, String title) {
	    return repo.getItemByExamIDAndPartTitle(assessmentId, title);
	}
	
	@Override
	public Optional<Item> findById(Long id) {
        return repo.findById(id);
    }  
}
