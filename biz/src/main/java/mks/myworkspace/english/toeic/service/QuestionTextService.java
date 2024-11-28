package mks.myworkspace.english.toeic.service;

import mks.myworkspace.english.toeic.entity.QuestionText;

public interface QuestionTextService {
    // Lấy QuestionText theo itemId
    QuestionText getQuestionTextByItemId(Long itemId);
}
