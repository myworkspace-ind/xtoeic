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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedassessment_t", uniqueConstraints = @UniqueConstraint(columnNames = "ID"))
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

	public Exam(Long id, String title, String description, String timeLimit, String dueDateTime) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.timeLimit = timeLimit;
		this.dueDateTime = dueDateTime;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", title=" + title + ", description=" + description + ", timeLimit=" + timeLimit
				+ ", dueDateTime=" + dueDateTime + "]";
	}

}
