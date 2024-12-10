package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedsection_t", uniqueConstraints = @UniqueConstraint(columnNames = "SECTIONID"))
@Getter
@Setter
@NoArgsConstructor
public class Part implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SECTIONID")
	private Long sectionId; // system field
	 
	@Column(name = "SEQUENCE")
	private Integer sequence;
	
	@Column(name = "TITLE", length = 99)
	private String title; 
	
	@ManyToOne
	@JoinColumn(name = "ASSESSMENTID", referencedColumnName = "ID")
	private Exam exam;
	 
	@OneToMany(mappedBy = "part")
	private List<Item> items;
	
	public Part(Long sectionId, Integer sequence, String title, Exam exam, List<Item> items) {
	    this.sectionId = sectionId;
	    this.sequence = sequence;
	    this.title = title;
	    this.exam = exam;
	    this.items = items;
	}

	@Override
	public String toString() {
	    return "Part [\n" +
	           "sectionId=" + sectionId + "\n" +
	           ", sequence=" + sequence + "\n" +
	           ", title='" + title + '\'' + "\n" +
	           ", exam=" + (exam != null ? exam.getId() : "null") + "\n" +  // Hiển thị id của Exam
	           ", itemsCount=" + (items != null ? items.size() : 0) + "\n" + // Hiển thị số lượng Item
	           ']';
	}

 
}
