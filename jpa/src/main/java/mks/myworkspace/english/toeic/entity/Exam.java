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
@Table(name = "xtoeic_exam", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
@Getter
@Setter
@NoArgsConstructor
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Exam implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // system field

	@Column(length = 99)
	private String title; 

	@Column(name = "time_limit", length = 99)
	private String timeLimit;

	@Column(name = "due_date_time", length = 99)
	private String dueDateTime;

	public Exam(Long id, String title, String timeLimit, String dueDateTime) {
		super();
		this.id = id;
		this.title = title;
		this.timeLimit = timeLimit;
		this.dueDateTime = dueDateTime;
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", title=" + title + ", timeLimit=" + timeLimit + ", dueDateTime=" + dueDateTime
				+ "]";
	}

}


//package mks.myworkspace.english.toeic.entity;
//
//public class Exam {
//    private String title;
//    private String timeLimit;
//    private String dueDateTime;  
//
//    public Exam(String title, String timeLimit, String dueDateTime) {
//        this.title = title;
//        this.timeLimit = timeLimit;
//        this.dueDateTime = dueDateTime;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getTimeLimit() {
//        return timeLimit;
//    }
//
//    public String getDueDateTime() {
//        return dueDateTime;
//    }
//
//}
