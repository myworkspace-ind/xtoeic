package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.model.ExamResult;

import mks.myworkspace.english.toeic.model.Exam;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamSection;


@Service
public interface ExamService2 {

    List<Exam> getExams();

    Exam getExamById(Integer id);

    ExamSection getSection(Integer examId, Integer sectNo);

    void attemptExam(Integer examId);

    ExamQuestionControl getQuestions(Integer examId, Integer sectNo, Integer quesNo);

    Map<Integer, List<ExamAnswer>> getExamAnswerSheet(Integer examId);

    void saveAnswer(Integer examId, Map<String, String> answerForm);

    Double submitAnswer(Integer examId, Map<String, String> answerForm);

    Page<ExamResult> getPagingResult(Integer examId, Pageable pageable);

}