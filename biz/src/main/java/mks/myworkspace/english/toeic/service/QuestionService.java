package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Question;

@Service
public interface QuestionService {
    List<Question> getQuestionsBySectionId(Long sectionId);
    
    
}
