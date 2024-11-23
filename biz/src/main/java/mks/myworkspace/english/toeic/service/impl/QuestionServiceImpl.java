package mks.myworkspace.english.toeic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Question;
import mks.myworkspace.english.toeic.repository.QuestionRepository;
import mks.myworkspace.english.toeic.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> getQuestionsBySectionId(Long sectionId) {
        return questionRepository.findQuestionsBySectionId(sectionId);
    }
    
    
}
