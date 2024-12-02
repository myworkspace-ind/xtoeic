package mks.myworkspace.english.toeic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;


@Repository

public interface AssessmentGradingRepository extends JpaRepository<AssessmentGrading, Long> {

//    @Modifying
//    @Query(value = "INSERT INTO sam_assessmentgrading_t " +
//                   "(AGENTID, ISLATE, FORGRADE, FINALSCORE, STATUS, HASAUTOSUBMISSIONRUN, PUBLISHEDASSESSMENTID) " +
//                   "VALUES (:agentId, 0, 0, 0, 0, 0, :publishedAssessmentId)", 
//           nativeQuery = true)
//    void insertAssessmentGrading(
//        @Param("agentId") String agentId, 
//        @Param("publishedAssessmentId") Long publishedAssessmentId);
    
//    @Query(value = "INSERT INTO sam_assessmentgrading_t " +
//            "(AGENTID, ISLATE, FORGRADE, FINALSCORE, STATUS, HASAUTOSUBMISSIONRUN, PUBLISHEDASSESSMENTID) " +
//            "VALUES ('LyHung', 0, 0, 0, 0, 0, 126)", 
//    nativeQuery = true)
//void insertAssessmentGrading();


 

//	 @Modifying
//	    @Transactional
//	    @Query(value = "INSERT INTO sam_assessmentgrading_t (AGENTID, ISLATE, FORGRADE, FINALSCORE, STATUS, HASAUTOSUBMISSIONRUN, PUBLISHEDASSESSMENTID) " +
//	                   "VALUES ('LyHung', 0, 0, 0, 0, 0, 126)", nativeQuery = true)
//	    void insertAssessmentGrading();
//    
//	 void deleteByAssessmentGradingId(Long id);
}

