package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PublishedAnswerRepository extends JpaRepository<PublishedAnswer, Integer> {

    List<PublishedAnswer> findAllByIdIn(Collection<Integer> id);

    List<PublishedAnswer> findAllByItemIdOrderBySequence(Integer id);

}
