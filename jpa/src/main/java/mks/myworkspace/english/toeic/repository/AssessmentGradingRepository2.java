package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.AssessmentGrading2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

@Repository
public interface AssessmentGradingRepository2 extends
        PagingAndSortingRepository<AssessmentGrading2, Integer>,
        JpaRepository<AssessmentGrading2, Integer> {

    Page<AssessmentGrading2> findAllByAgentIdAndAssessmentIdAndStatus(
            String agentId,
            Integer assessmentId,
            Integer status,
            Pageable pageable);

    AssessmentGrading2 findByAgentIdAndAssessmentIdAndStatus(String agentId, Integer assessmentId, Integer status);

    AssessmentGrading2 findByAgentIdAndIdAndStatus(String agentId, Integer assessmentId, Integer status);


    @Query(value = "select   \n" +
            "\tCOALESCE(SUM(CASE WHEN sc.TITLE  in ('Part1','Part2','Part3','Part4') THEN 1 ELSE 0 END),0) as listeningScore,\n" +
            "\tCOALESCE(SUM(CASE WHEN sc.TITLE  in ('Part5','Part6','Part7') THEN 1 ELSE 0 END),0) as readingScore\n" +
            "from sam_assessmentgrading_t sat \n" +
            "join sam_itemgrading_t sit on sit.ASSESSMENTGRADINGID = sat.ASSESSMENTGRADINGID \n" +
            "join sam_publisheditem_t spt on spt.ITEMID  = sit.ITEMGRADINGID \n" +
            "join sam_publishedsection_t sc on sc.SECTIONID  = spt.SECTIONID \n" +
            "where sat.AGENTID = '70a9eec6-9663-40ad-aa1c-120dbfc9665d'\n" +
            "and sat.ASSESSMENTGRADINGID = :gradingId " +
            "and sit.ISCORRECT = '1'", nativeQuery = true)
    Map<String,Object> getScoreByPart(@Param("gradingId") Integer gradingId);

}
