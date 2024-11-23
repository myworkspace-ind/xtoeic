package mks.myworkspace.english.toeic.service;

import java.util.List;
import mks.myworkspace.english.toeic.entity.QuestionText;

public interface QuestionTextService {
    List<QuestionText> getQuestionTextsByItemId(Long itemId);
}
