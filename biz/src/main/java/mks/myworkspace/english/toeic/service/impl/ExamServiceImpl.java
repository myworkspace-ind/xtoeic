package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.ExamRepository;
import mks.myworkspace.english.toeic.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService{

	@Autowired
	private ExamRepository repo;
	
	@Override
	public ExamRepository getRepo() {
		return repo;
	}
	
	@Override
	public List<Exam> getAllExams() {	
		return repo.findAll();
	}
	
	@Override
	public List<Exam> getExamsWithETSTitlePrefix() {	
		return repo.findExamsWithETSTitlePrefix();
	}
	
	@Override
	public List<Exam> getExamsWithETSTitleAndPracticeType() {	
		return repo.findExamsWithETSTitleAndPracticeType();
	}
	
	@Override
	public List<Exam> getExamsWithETSTitleAndExamType() {	
		return repo.findExamsWithETSTitleAndExamType();
	}
	
	@Override
	public Optional<Exam> findById(Long id) {
        return repo.findById(id);
    }  
}
