package mks.myworkspace.english.toeic.service;

import java.util.List;
import mks.myworkspace.english.toeic.entity.Question;

public interface QuestionService {
    List<Question> getQuestionsBySectionId(Long sectionId);
}
