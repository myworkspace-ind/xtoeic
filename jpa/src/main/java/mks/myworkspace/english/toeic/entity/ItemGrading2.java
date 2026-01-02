package mks.myworkspace.english.toeic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "sam_itemgrading_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemGrading2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMGRADINGID")
    private Integer id;

    @Column(name = "ASSESSMENTGRADINGID")
    private Integer assessmentGradingId;

    @Column(name = "PUBLISHEDITEMID")
    private Integer publishedItemId;

    @Column(name = "PUBLISHEDITEMTEXTID")
    private Integer publishedItemTextId;

    @Column(name = "AGENTID")
    private String agentId;

    @Column(name = "SUBMITTEDDATE")
    private Date submittedDate;

    @Column(name = "PUBLISHEDANSWERID")
    private Integer publishedAnswerId;

    @Column(name = "RATIONALE")
    private String rationale;

    @Column(name = "ANSWERTEXT")
    private String answerText;

    @Column(name = "AUTOSCORE")
    private Double autoScore;

    @Column(name = "OVERRIDESCORE")
    private Double overrideScore;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "GRADEDBY")
    private String gradedBy;

    @Column(name = "GRADEDDATE")
    private Date gradedDate;

    @Column(name = "REVIEW")
    private Boolean review;

    @Column(name = "ATTEMPTSREMAINING")
    private Integer attemptsRemaining;

    @Column(name = "LASTDURATION")
    private String lastDuration;

    @Column(name = "ISCORRECT")
    private Boolean isCorrect;

}
