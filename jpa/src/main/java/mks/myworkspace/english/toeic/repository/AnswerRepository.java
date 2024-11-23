package mks.myworkspace.english.toeic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	/*
	 * List<Answer> findAll();
	 * 
	 * List<Answer> findByItemIdAndItemTextId(Long itemId, Long itemTextId);
	 */
}

