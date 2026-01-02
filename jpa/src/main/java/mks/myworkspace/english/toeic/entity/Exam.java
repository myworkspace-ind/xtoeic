package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedassessment_t", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
@Immutable
@Getter
@Setter
@NoArgsConstructor
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id; // system field

	@Column(name = "TITLE", length = 99)
	private String title;
	
	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "time_limit", length = 99)
	private String timeLimit;

	@Column(name = "due_date_time", length = 99)
	private String dueDateTime;
	
	@OneToMany(mappedBy = "exam")
	private List<Part> parts;
	
	@OneToMany(mappedBy = "exam")
	private List<AssessmentGrading> assessmentGradings;
	

	public Exam(Long id, String title, String description, String timeLimit, String dueDateTime,
            List<Part> parts, List<AssessmentGrading> assessmentGradings) {
	    this.id = id; // system field
	    this.title = title;
	    this.description = description;
	    this.timeLimit = timeLimit;
	    this.dueDateTime = dueDateTime;
	    this.parts = parts; // Danh sách các phần liên quan
	    this.assessmentGradings = assessmentGradings; // Danh sách các bài chấm điểm
	}
	@Override
	public String toString() {
	    return "Exam [\n" +
	           "id=" + id + "\n" +
	           ", title='" + title + '\'' + "\n" +
	           ", description='" + description + '\'' + "\n" +
	           ", timeLimit='" + timeLimit + '\'' + "\n" +
	           ", dueDateTime='" + dueDateTime + '\'' + "\n" +
	           
	           ']';
	}
	
//	@Override
//	public String toString() {
//	    return "Exam [\n" +
//	           "id=" + id + "\n" +
//	           ", title='" + title + '\'' + "\n" +
//	           ", description='" + description + '\'' + "\n" +
//	           ", timeLimit='" + timeLimit + '\'' + "\n" +
//	           ", dueDateTime='" + dueDateTime + '\'' + "\n" +
//	           ", partsCount=" + (parts != null ? parts.size() : 0) + "\n" + // Hiển thị số lượng Part
//	           ", assessmentGradingsCount=" + (assessmentGradings != null ? assessmentGradings.size() : 0) + "\n" + // Hiển thị số lượng AssessmentGrading
//	           ']';
//	}

}
