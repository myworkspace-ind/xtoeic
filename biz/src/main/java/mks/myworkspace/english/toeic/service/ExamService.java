package mks.myworkspace.english.toeic.service;

import mks.myworkspace.english.toeic.entity.PublishedAssessment;
import mks.myworkspace.english.toeic.model.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.model.Exam;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamSection;

@Service
public interface ExamService {

//    ExamRepository getRepo();
//
//    List<Exam> getAllExams(); 
//    
//    List<Exam> getExamsWithETSTitlePrefix();
//    
//    List<Exam> getExamsWithETSTitleAndPracticeType();
//
//    List<Exam> getExamsWithETSTitleAndExamType();
//    
//    Optional<Exam> findById(Long id);
//    
//    List<Question> getAllQuestions();
	List<Exam> getExams();

    Exam getExamById(Integer id);

    ExamSection getSection(Integer examId, Integer sectNo);

    ExamQuestionControl getQuestions(Integer examId, Integer sectNo, Integer quesNo);

    Map<Integer, List<ExamAnswer>> getExamAnswerSheet(Integer examId);

    Integer submitAnswer(Integer examId, Map<Integer, String> answerForm);

}
