package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Answer;

@Service
public interface AnswerService {
	List<Answer> getAnswersByQuestionId(Long questionId);
}
