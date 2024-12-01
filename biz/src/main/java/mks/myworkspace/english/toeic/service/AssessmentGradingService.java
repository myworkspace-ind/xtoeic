package mks.myworkspace.english.toeic.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;

@Service
public interface AssessmentGradingService {

	AssessmentGradingRepository getRepo();
	
	Optional<AssessmentGrading> findById(Long id);
	
	AssessmentGrading saveOrUpdate(AssessmentGrading grading);
}
