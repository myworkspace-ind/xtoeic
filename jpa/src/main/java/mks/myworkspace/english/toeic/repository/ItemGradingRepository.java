package mks.myworkspace.english.toeic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.ItemGrading;

@Repository
public interface ItemGradingRepository extends JpaRepository<ItemGrading, Long> {
	// Custom query methods nếu cần
	public List<ItemGrading> findByAssessmentGrading_AssessmentGradingId(Long assessmentGradingId);
}
