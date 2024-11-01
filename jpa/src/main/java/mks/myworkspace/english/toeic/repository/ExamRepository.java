package mks.myworkspace.english.toeic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
	List<Exam> findAll();
//	List<Exam> findByTitleContainingIgnoreCaseOrTimeLimitContainingIgnoreCaseOrDueDateTimeContainingIgnoreCase(String title, String timeLimit, String dueDateTime);

}