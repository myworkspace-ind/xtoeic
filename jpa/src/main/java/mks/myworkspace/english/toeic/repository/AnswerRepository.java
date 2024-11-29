package mks.myworkspace.english.toeic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    // Custom query methods nếu cần
	
	@Query("SELECT a FROM Answer a " +
           "WHERE a.itemText.itemTextId = :itemTextId " +
           "ORDER BY a.label")
    List<Answer> getAnswersByItemTextId(@Param("itemTextId") Long itemTextId);
	 
}
