package mks.myworkspace.english.toeic.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
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

	public AssessmentGrading saveOrUpdate(AssessmentGrading assessmentGrading) {  
		Long id = appRepo.saveOrUpdate(assessmentGrading);
		if (id != null) {
			assessmentGrading.setAssessmentGradingId(id);
		}
		return assessmentGrading;
	} 
}
