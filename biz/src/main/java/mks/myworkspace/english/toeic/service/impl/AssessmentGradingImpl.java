package mks.myworkspace.english.toeic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;
import mks.myworkspace.english.toeic.repository.ItemTextRepository;
import mks.myworkspace.english.toeic.service.AssessmentGradingService;
import mks.myworkspace.english.toeic.service.ItemTextService;

@Service
public class AssessmentGradingImpl implements AssessmentGradingService{
	
	@Autowired
	private AssessmentGradingRepository repo;
	
	@Override
	public AssessmentGradingRepository getRepo() {
		return repo;
	}

	@Override
	public <S extends AssessmentGrading> S save(S entity) {
		return repo.save(entity);
	} 
 
	
}
