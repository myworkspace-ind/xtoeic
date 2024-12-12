package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishedSectionRepository extends JpaRepository<PublishedSection, Integer> {

}
