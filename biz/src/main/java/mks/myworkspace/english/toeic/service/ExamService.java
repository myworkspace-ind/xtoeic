package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.ExamRepository;

@Service
public interface ExamService {

    ExamRepository getRepo();

    List<Exam> getAllExams(); 
    
}
