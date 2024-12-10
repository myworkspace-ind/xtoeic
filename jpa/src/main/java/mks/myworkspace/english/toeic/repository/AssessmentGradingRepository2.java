package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.AssessmentGrading2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AssessmentGradingRepository2 extends
    PagingAndSortingRepository<AssessmentGrading2, Integer>,
    JpaRepository<AssessmentGrading2, Integer> {

    Page<AssessmentGrading2> findAllByAgentIdAndAssessmentId(
        String agentId,
        Integer assessmentId,
        Pageable pageable);

    AssessmentGrading2 findByAgentIdAndAssessmentIdAndStatus(String agentId, Integer assessmentId, Integer status);

}
