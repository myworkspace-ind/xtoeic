package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishedAssessmentRepository extends JpaRepository<PublishedAssessment, Integer> {

}
