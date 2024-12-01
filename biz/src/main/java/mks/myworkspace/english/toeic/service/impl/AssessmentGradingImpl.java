package mks.myworkspace.english.toeic.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.AppRepository;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;
import mks.myworkspace.english.toeic.service.AssessmentGradingService;

@Service
@Slf4j
public class AssessmentGradingImpl implements AssessmentGradingService {

	@Autowired
	private AssessmentGradingRepository repo;
	
	@Autowired
	@Getter
	AppRepository appRepo;

	@Override
	public AssessmentGradingRepository getRepo() {
		return repo;
	}
	
	@Override
	public Optional<AssessmentGrading> findById(Long id) {
		return repo.findById(id);
	}

	@Override
	public AssessmentGrading saveOrUpdate(AssessmentGrading grading) {  
		Long id = appRepo.saveOrUpdate(grading);
		if (id != null) {
			grading.setAssessmentGradingId(id);
		}
		return grading;
	}
}
