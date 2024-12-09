package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AssessmentGradingRepository extends
    PagingAndSortingRepository<AssessmentGrading, Integer>,
    JpaRepository<AssessmentGrading, Integer> {

    Page<AssessmentGrading> findAllByAgentIdAndAssessmentId(
        String agentId,
        Integer assessmentId,
        Pageable pageable);

    AssessmentGrading findByAgentIdAndAssessmentIdAndStatus(String agentId, Integer assessmentId, Integer status);

}
