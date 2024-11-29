package mks.myworkspace.english.toeic.entity; 

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_assessmentgrading_t", uniqueConstraints = @UniqueConstraint(columnNames = "ASSESSMENTGRADINGID"))
@Getter
@Setter
@NoArgsConstructor
public class AssessmentGrading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSESSMENTGRADINGID")
    private Long assessmentGradingId; // system field

    @ManyToOne
    @JoinColumn(name = "PUBLISHEDASSESSMENTID", referencedColumnName = "ID")
    private Exam exam; // Tham chiếu đến Exam

    @Column(name = "AGENTID")
    private String agentId;

    @Column(name = "ATTEMPTDATE")
    private LocalDateTime attemptDate;

    @Column(name = "SUBMITTEDDATE")
    private LocalDateTime submittedDate;
    
    // Mấy cái dưới thêm vào là do ràng buộc not null
    @Column(name = "FORGRADE") // Bấm start là 0, Bấm submit là 1 
    private int forGrade;

    @Column(name = "STATUS") // Bấm start là 0, Bấm submit là 1 
    private int status;

    @Column(name = "ISLATE") // Gán cố định là 0
    private int isLate;

    @Column(name = "HASAUTOSUBMISSIONRUN") // Gán cố định là 0
    private int hasAutoSubmissionRun;

    // Constructor
    public AssessmentGrading(Long assessmentGradingId, Exam exam, String agentId, LocalDateTime attemptDate,
			LocalDateTime submittedDate, int forGrade, int status, int isLate, int hasAutoSubmissionRun) {
		super();
		this.assessmentGradingId = assessmentGradingId;
		this.exam = exam;
		this.agentId = agentId;
		this.attemptDate = attemptDate;
		this.submittedDate = submittedDate;
		this.forGrade = forGrade;
		this.status = status;
		this.isLate = isLate;
		this.hasAutoSubmissionRun = hasAutoSubmissionRun;
	}

    @Override
    public String toString() {
        return "AssessmentGrading [\n" +
               "assessmentGradingId=" + assessmentGradingId + "\n" +
               ", exam=" + (exam != null ? exam.getId() : "null") + "\n" + // Hiển thị ID của Exam nếu không null 
               ", examTitle=" + (exam != null ? exam.getTitle() : "null") + "\n" +
               ", agentId='" + agentId + '\'' + "\n" +
               ", attemptDate=" + attemptDate + "\n" +
               ", submittedDate=" + submittedDate + "\n" +
               ", forGrade=" + forGrade + "\n" +  
               ", status=" + status + "\n" +  
               ", isLate=" + isLate + "\n" +  
               ", hasAutoSubmissionRun=" + hasAutoSubmissionRun + "\n" +  
               ']';
    }

	
}
