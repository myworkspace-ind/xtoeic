package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.entity.Question;
import mks.myworkspace.english.toeic.repository.AnswerRepository;
import mks.myworkspace.english.toeic.repository.QuestionRepository;
import mks.myworkspace.english.toeic.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
    public List<Question> getQuestionsBySectionId(Long sectionId) {
        // Lấy danh sách câu hỏi từ repository và sắp xếp theo sequence
        List<Question> questions = questionRepository.findQuestionsBySectionId(sectionId);

        // Duyệt qua từng câu hỏi và gán đáp án hợp lệ
        for (Question question : questions) {
            List<Answer> validAnswers = getValidAnswersForQuestion(question.getId());
            question.setAnswers(validAnswers);
        }

        return questions;
    }

    // Hàm giúp lọc các đáp án hợp lệ
    private List<Answer> getValidAnswersForQuestion(Long questionId) {
        // Lấy danh sách các đáp án từ repository
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        // Lọc bỏ các đáp án không hợp lệ (có text null hoặc trống)
        return answers.stream()
                      .filter(answer -> answer.getText() != null && !answer.getText().trim().isEmpty())
                      .collect(Collectors.toList());
    }

}
