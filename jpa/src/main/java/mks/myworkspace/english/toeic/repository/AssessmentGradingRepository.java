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
    // Custom query methods nếu cần  	
	  
}

