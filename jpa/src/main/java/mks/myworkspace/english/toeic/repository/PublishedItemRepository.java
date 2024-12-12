package mks.myworkspace.english.toeic.repository;

import mks.myworkspace.english.toeic.entity.PublishedItem;
import mks.myworkspace.english.toeic.model.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublishedItemRepository extends JpaRepository<PublishedItem, Integer> {

    @Query(value = "select ite.ITEMID, tex.TEXT\n" +
        " from sam_publisheditem_t ite\n" +
        " join sam_publisheditemtext_t tex\n" +
        " on ite.ITEMID = tex.ITEMID\n" +
        " where ite.ITEMID = :id", nativeQuery = true)
    Optional<ExamQuestion> queryFindById(@Param("id") Integer id);

    @Query(value = "select count(1)\n" +
        " from sam_publishedassessment_t ass\n" +
        " join sam_publishedsection_t sec\n" +
        " on ass.id = sec.ASSESSMENTID\n" +
        " and ass.id = :examId\n" +
        " join sam_publisheditem_t ite\n" +
        " on sec.SECTIONID = ite.SECTIONID\n" +
        " and (sec.SEQUENCE < (select SEQUENCE from sam_publishedsection_t where SECTIONID = :secId)\n" +
        " or (sec.SEQUENCE = (select SEQUENCE from sam_publishedsection_t where SECTIONID = :secId)\n" +
        " and ite.SEQUENCE <= (select SEQUENCE from sam_publisheditem_t where ITEMID = :itemId)))\n" +
        " order by sec.SEQUENCE, ite.SEQUENCE;", nativeQuery = true)
    Integer queryCountQuestionNo(@Param("examId") Integer examId, @Param("secId") Integer secId, @Param("itemId") Integer itemId);

}
