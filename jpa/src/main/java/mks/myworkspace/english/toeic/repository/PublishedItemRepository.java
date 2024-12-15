package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishedItemRepository extends JpaRepository<PublishedItem, Integer> {

    Integer countAllBySectionIdIn(List<Integer> sectionIds);

    Integer countAllBySectionId(Integer sectionId);

    List<PublishedItem> findAllBySectionIdOrderBySequence(Integer sectionId);

}
