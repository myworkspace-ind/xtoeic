package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedAnswer;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PublishedAnswerRepository extends JpaRepository<PublishedAnswer, Integer> {

    @Query(value = " select sec.SEQUENCE as section_no, ite.SEQUENCE as item_no, ans.*\n" +
        " from sam_publishedassessment_t ass\n" +
        " join sam_publishedsection_t sec\n" +
        " on ass.id = :examId\n" +
        " and ass.id = sec.ASSESSMENTID\n" +
        " join sam_publisheditem_t ite\n" +
        " on sec.SECTIONID = ite.SECTIONID\n" +
        " join sam_publisheditemtext_t tex\n" +
        " on ite.ITEMID = tex.ITEMID\n" +
        " join sam_answer_t ans\n" +
        " on ans.ITEMID = ite.ITEMID\n" +
        " order by sec.SEQUENCE, ite.SEQUENCE, ans.SEQUENCE", nativeQuery = true)
    List<ExamAnswer> findAllByExamId(@Param("examId") Integer examId);

    @Query(value = " select null as section_no, null as item_no, ans.*\n" +
        " from sam_answer_t ans\n" +
        " where ITEMID = :questionId" +
        " order by ans.SEQUENCE", nativeQuery = true)
    List<ExamAnswer> findAllByQuestionId(@Param("questionId") Integer questionId);

    List<PublishedAnswer> findAllByIdIn(Collection<Integer> id);

}
