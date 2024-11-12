package mks.myworkspace.english.toeic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
	
	List<Exam> findAll();
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%'")
	List<Exam> findExamsWithETSTitlePrefix();
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%' AND e.description LIKE '%Type: Practice%'")
	List<Exam> findExamsWithETSTitleAndPracticeType();
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%' AND e.description LIKE '%Type: Exam%'")
	List<Exam> findExamsWithETSTitleAndExamType();

}