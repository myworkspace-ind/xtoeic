package mks.myworkspace.english.toeic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
	
	@Query("SELECT s FROM Section s WHERE s.assessmentId = :assessmentId")
    List<Section> findSectionsByAssessmentId(@Param("assessmentId") Long assessmentId);
	
}

