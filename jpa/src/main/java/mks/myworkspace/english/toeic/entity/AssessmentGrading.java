package mks.myworkspace.english.toeic.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Table(name = "sam_assessmentgrading_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentGrading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSESSMENTGRADINGID")
    private Integer id;

    @Column(name = "PUBLISHEDASSESSMENTID")
    private Integer assessmentId;

    @Column(name = "AGENTID")
    private String agentId;

    @Column(name = "SUBMITTEDDATE")
    private Date submittedDate;

    @Column(name = "ISLATE")
    private Boolean isLate;

    @Column(name = "FORGRADE")
    private Boolean forGrade;

    @Column(name = "TOTALAUTOSCORE")
    private Double totalAutoScore;

    @Column(name = "TOTALOVERRIDESCORE")
    private Double totalOverrideScore;

    @Column(name = "FINALSCORE")
    private Double finalScore;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "GRADEDBY")
    private String gradeBy;

    @Column(name = "GRADEDDATE")
    private Date gradedDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "ATTEMPTDATE")
    private Date attemptDate;

    @Column(name = "TIMEELAPSED")
    private Integer timeElapsed;

    @Column(name = "ISAUTOSUBMITTED")
    private Boolean isAutoSubmitted;

    @Column(name = "LASTVISITEDPART")
    private Integer lastVisitedPart;

    @Column(name = "LASTVISITEDQUESTION")
    private Integer lastVisitedQuestion;

    @Column(name = "HASAUTOSUBMISSIONRUN")
    private Boolean hasAutoSubmissionRun;

}
