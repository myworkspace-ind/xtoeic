package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.QuestionText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionTextRepository extends JpaRepository<QuestionText, Long> {

    // Lấy danh sách `QuestionText` theo `ITEMID`
    List<QuestionText> findByItemId(Long itemId);
}
