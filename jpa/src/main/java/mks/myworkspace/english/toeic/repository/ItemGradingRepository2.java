package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.ItemGrading2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemGradingRepository2 extends JpaRepository<ItemGrading2, Integer> {

    ItemGrading2 findByAssessmentGradingIdAndPublishedItemId(
            Integer assessmentGradingId,
            Integer publishedItemId
    );

    List<ItemGrading2> findAllByAssessmentGradingId(
            Integer assessmentGradingId
    );

}
