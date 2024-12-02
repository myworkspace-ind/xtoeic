package mks.myworkspace.english.toeic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;
import mks.myworkspace.english.toeic.repository.ExamRepository;

@Service
public interface AssessmentGradingService {

	AssessmentGradingRepository getRepo();

	<S extends AssessmentGrading> S save(S entity);

	// void insertAssessmentGrading( );

	void deleteByAssessmentGradingId(Long id);

}
