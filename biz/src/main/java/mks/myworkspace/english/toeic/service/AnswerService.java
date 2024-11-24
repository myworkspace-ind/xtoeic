package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.repository.AnswerRepository;

@Service
public interface AnswerService {
	
	AnswerRepository getRepo();
	
	List<Answer> getAnswersByItemTextId(Long itemTextId); 
}
