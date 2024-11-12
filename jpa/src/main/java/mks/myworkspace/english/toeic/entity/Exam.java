package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID; // system field

	@Column(name = "TITLE", length = 99)
	private String title;
	
	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "time_limit", length = 99)
	private String timeLimit;

	@Column(name = "due_date_time", length = 99)
	private String dueDateTime;

	@Override
	public String toString() {
		return "Exam [ID=" + ID + ", title=" + title + ", timeLimit=" + timeLimit + ", dueDateTime=" + dueDateTime
				+ "]";
	}

	public Exam(Long iD, String title, String timeLimit, String dueDateTime) {
		super();
		ID = iD;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dueDateTime = dueDateTime;
	}


}
