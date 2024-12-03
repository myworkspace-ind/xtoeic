package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.AnswerRepository;

@Service
public interface AnswerService {
	
	AnswerRepository getRepo();
	
	List<Answer> getAnswersByItemTextId(Long itemTextId); 
	
	Optional<Answer> findById(Long id);
	List<String> findFeedbackTextsByItemTextId(Long itemTextId);
}
