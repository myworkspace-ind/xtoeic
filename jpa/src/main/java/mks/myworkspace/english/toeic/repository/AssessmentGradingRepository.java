package mks.myworkspace.english.toeic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Part;

@Repository
public interface AssessmentGradingRepository extends JpaRepository<AssessmentGrading, Long> {
    // Custom query methods nếu cần
}
