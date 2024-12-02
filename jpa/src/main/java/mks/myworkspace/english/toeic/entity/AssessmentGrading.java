package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_assessmentgrading_t")
@Getter
@Setter
@NoArgsConstructor
public class AssessmentGrading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSESSMENTGRADINGID")
    private Long assessmentGradingId; // system field

    

    @Column(name = "AGENTID")
    private String agentId;
    
    @Column(name = "ISLATE")
    private int islate;

    @Column(name = "FORGRADE")
    private int forGrade;
    
    @Column(name = "FINALSCORE")
    private int finalScore;	
    
    @Column(name = "STATUS")
    private int status;
    
    @Column(name = "HASAUTOSUBMISSIONRUN")
    private int hasauToSubmissIOnRun;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PUBLISHEDASSESSMENTID", referencedColumnName = "ID")
    private Exam exam; // Tham chiếu đến Exam

}