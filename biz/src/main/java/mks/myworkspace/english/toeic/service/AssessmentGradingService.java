package mks.myworkspace.english.toeic.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;

@Service
public interface AssessmentGradingService {

	AssessmentGradingRepository getRepo();

//	<S extends AssessmentGrading> S save(S entity);
	  
	void insertAssessmentGrading(AssessmentGrading grading);
	
	void insertAssessmentGrading(Long publishedAssessmentId, String agentId, LocalDateTime attemptDate, 
            LocalDateTime submittedDate, boolean forGrade, int status, 
            boolean isLate, boolean hasAutoSubmissionRun);
}
