package mks.myworkspace.english.toeic.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	@Query("SELECT q FROM Question q WHERE q.section.id = :sectionId ORDER BY q.sequence")
    List<Question> findQuestionsBySectionId(@Param("sectionId") Long sectionId);

	// Thêm phương thức truy vấn theo sectionId (tương đương SELECT * FROM sam_publisheditem_t WHERE SECTIONID = :sectionId)
	List<Question> findBySectionId(Long sectionId);
}

