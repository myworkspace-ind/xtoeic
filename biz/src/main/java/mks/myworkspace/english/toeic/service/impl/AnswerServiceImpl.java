package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.AnswerRepository;
import mks.myworkspace.english.toeic.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService  { 
	
	@Autowired
	private AnswerRepository repo;
	
	@Override
	public AnswerRepository getRepo() {
		return repo;
	} 
 
    @Override
    public List<Answer> getAnswersByItemTextId(Long itemTextId) {
        return repo.getAnswersByItemTextId(itemTextId);
    }
    
    @Override
	public Optional<Answer> findById(Long id) {
        return repo.findById(id);
    }  
}
