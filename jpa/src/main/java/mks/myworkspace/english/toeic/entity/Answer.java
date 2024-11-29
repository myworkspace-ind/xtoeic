package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

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
@Table(name = "sam_publishedanswer_t",  uniqueConstraints = @UniqueConstraint(columnNames = "ANSWERID"))
@Getter
@Setter
@NoArgsConstructor
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWERID")
    private Long answerId; // system field

//    @Column(name = "ITEMTEXTID")
//    private Long itemTextId; // Foreign key referencing ItemText entity
    
    @Column(name = "SEQUENCE")
	private Integer sequence;

    @Column(name = "TEXT", length = 500)
    private String text; // The answer text
    
    @Column(name = "LABEL", length = 500)
    private String label;  // A, B, C, D	

    @Column(name = "ISCORRECT")
    private Boolean isCorrect; // Whether the answer is correct
    
    @ManyToOne
    @JoinColumn(name = "ITEMTEXTID", referencedColumnName = "ITEMTEXTID")
	private ItemText itemText;
    
    public Answer(Long answerId, Integer sequence, String text, String label, Boolean isCorrect, ItemText itemText) {
		this.answerId = answerId;
		this.sequence = sequence;
		this.text = text;
		this.label = label;
		this.isCorrect = isCorrect;
		this.itemText = itemText;
	}
    
    @Override
    public String toString() {
        return "Answer [\n" +
               "answerId=" + answerId + "\n" +
               ", sequence=" + sequence + "\n" +
               ", text='" + text + '\'' + "\n" +
               ", label='" + label + '\'' + "\n" +
               ", isCorrect=" + isCorrect + "\n" +
               ", itemText=" + (itemText != null ? itemText.getItemTextId() : "null") + "\n" +
               ']';
    }




	
}
