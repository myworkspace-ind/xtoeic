package mks.myworkspace.english.toeic.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.Exam;




@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
	
	// List<Exam> findAll(); // findById cũng không cần phải khai báo. Do:
	
	/*
	  	JpaRepository (mà ExamRepository kế thừa) đã cung cấp sẵn các phương thức cơ bản như:
			findAll()
			save()
			deleteById()
			findById()
			Và nhiều phương thức khác (CRUD).
		Do đó, nếu chỉ sử dụng các phương thức mặc định của JPA, bạn không cần phải khai báo lại.
	 */
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%'")
	List<Exam> findExamsWithETSTitlePrefix();
	
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%' AND e.description LIKE '%Type: Practice%'")
	List<Exam> findExamsWithETSTitleAndPracticeType();
	
	@Query("SELECT e FROM Exam e WHERE e.title LIKE 'ETS%' AND e.description LIKE '%Type: Exam%'")
	List<Exam> findExamsWithETSTitleAndExamType();
	 
	@Query("SELECT item, itemText, answer " +
	        "FROM Part part " +
	        "JOIN part.items item " +
	        "JOIN item.itemTexts itemText " +
	        "JOIN itemText.answers answer " +
	        "WHERE part.exam.id = :examId " +
	        "AND part.title = 'Part1' " +
	        "AND item.sequence = 1 " +
	        "ORDER BY answer.label")
	List<Object[]> findExamPart1Details(@Param("examId") Long examId);
	
	@Query("SELECT item, itemText, answer " +
		       "FROM Part part " +
		       "JOIN part.items item " +
		       "JOIN item.itemTexts itemText " +
		       "JOIN itemText.answers answer " +
		       "WHERE part.exam.id = :examId " +
		       "AND part.title = 'Part1' " +
		       "AND answer.label = 'A' " +
		       "ORDER BY item.sequence, answer.label")
		List<Object[]> findAllExamPart1Details(@Param("examId") Long examId);
		
		
	
	
		


}