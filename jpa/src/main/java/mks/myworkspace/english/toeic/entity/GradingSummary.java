package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_gradingsummary_t")
@Getter
@Setter
@NoArgsConstructor
public class GradingSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id; // system field

    @Column(name = "ASSESSMENTID")
    private Long assessmentId; // Foreign key referencing Exam entity

    @Column(name = "TOTALSCORE")
    private Double totalScore;

    @Column(name = "GRADE")
    private String grade;

    @Override
    public String toString() {
        return "GradingSummary [id=" + id + ", assessmentId=" + assessmentId + ", totalScore=" + totalScore
                + ", grade=" + grade + "]";
    }
}
