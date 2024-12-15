package mks.myworkspace.english.toeic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Custom query methods nếu cần
	
	@Query("SELECT item " +
	           "FROM Part part " +
	           "JOIN part.items item " +
	           "JOIN item.itemTexts itemText " +
	           "WHERE part.exam.id = :assessmentId " +
	           "AND part.title = :title " +
	           "ORDER BY item.sequence")
    List<Item> getItemByExamIDAndPartTitle(@Param("assessmentId") Long assessmentId, 
	                                                             @Param("title") String title);
}
