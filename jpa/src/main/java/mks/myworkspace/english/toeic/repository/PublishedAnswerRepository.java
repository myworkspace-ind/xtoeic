package mks.myworkspace.english.toeic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mks.myworkspace.english.toeic.entity.PublishedAnswer;

@Repository
public interface PublishedAnswerRepository extends JpaRepository<PublishedAnswer, Integer> {

	List<PublishedAnswer> findAllByIdIn(Collection<Integer> id);

    List<PublishedAnswer> findAllByItemIdOrderBySequence(Integer id);

}
