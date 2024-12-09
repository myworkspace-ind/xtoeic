package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.ItemGrading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemGradingRepository extends JpaRepository<ItemGrading, Integer> {

    ItemGrading findByAssessmentGradingIdAndPublishedItemId(
        Integer assessmentGradingId,
        Integer publishedItemId
    );

    List<ItemGrading> findAllByAssessmentGradingId(
        Integer assessmentGradingId
    );

}
