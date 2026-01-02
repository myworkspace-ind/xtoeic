package mks.myworkspace.english.toeic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedaccesscontrol_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedAccessControl {

    @Id
    @Column(name = "ASSESSMENTID")
    private Integer assessmentId;

    @Column(name = "UNLIMITEDSUBMISSIONS")
    private Boolean unlimitedSubmissions;

    @Column(name = "SUBMISSIONSALLOWED")
    private Integer submissionsAllowed;

    @Column(name = "SUBMISSIONSSAVED")
    private Integer submissionsSaved;

    @Column(name = "ASSESSMENTFORMAT")
    private Integer assessmentFormat;

    @Column(name = "BOOKMARKINGITEM")
    private Integer bookmarkingItem;

    @Column(name = "TIMELIMIT")
    private Integer timeLimit;

    @Column(name = "TIMEDASSESSMENT")
    private Integer timedAssessment;

    @Column(name = "RETRYALLOWED")
    private Integer retryAllowed;

    @Column(name = "LATEHANDLING")
    private Integer lateHandling;

    @Column(name = "INSTRUCTORNOTIFICATION")
    private Integer instructorNotification;

    @Column(name = "STARTDATE")
    private Date startDate;

    @Column(name = "DUEDATE")
    private Date dueDate;

    @Column(name = "SCOREDATE")
    private Date scoreDate;

    @Column(name = "FEEDBACKDATE")
    private Date feedbackDate;

    @Column(name = "RETRACTDATE")
    private Date retractDate;

    @Column(name = "AUTOSUBMIT")
    private Integer autoSubmit;

    @Column(name = "ITEMNAVIGATION")
    private Integer itemNavigation;

    @Column(name = "ITEMNUMBERING")
    private Integer itemNumbering;

    @Column(name = "DISPLAYSCORE")
    private Integer displayScore;

    @Column(name = "SUBMISSIONMESSAGE")
    private String submissionMessage;

    @Column(name = "RELEASETO")
    private String releaseTo;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FINALPAGEURL")
    private String finalPageUrl;

    @Column(name = "MARKFORREVIEW")
    private Integer markForReview;

    @Column(name = "HONORPLEDGE")
    private Boolean honorPledge;

    @Column(name = "FEEDBACKENDDATE")
    private Date feedbackEndDate;

    @Column(name = "FEEDBACKSCORETHRESHOLD")
    private Double feedbackScoreThreshold;


    @OneToOne
    @MapsId
    @JoinColumn(name = "ASSESSMENTID")
    private PublishedAssessment publishedAssessment;

}
