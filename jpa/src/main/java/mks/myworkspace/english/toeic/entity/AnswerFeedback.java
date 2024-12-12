package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedanswerfeedback_t",  uniqueConstraints = @UniqueConstraint(columnNames = "ANSWERFEEDBACKID"))
@Getter
@Setter
@NoArgsConstructor
public class AnswerFeedback implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWERFEEDBACKID")
    private Long answerFeedbackId;

	@Column(name = "TEXT", length = 4000)
    private String text;
	
	@OneToOne
    @JoinColumn(name = "ANSWERID", referencedColumnName = "ANSWERID", nullable = false)
    private Answer answer;
	
	
}
