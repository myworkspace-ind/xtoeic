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
@Table(name = "sam_itemgrading_t", uniqueConstraints = @UniqueConstraint(columnNames = "ITEMGRADINGID"))
@Getter
@Setter
@NoArgsConstructor
public class ItemGrading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMGRADINGID")
    private Long itemGradingId; // system field
  
    @ManyToOne
    @JoinColumn(name = "ASSESSMENTGRADINGID", referencedColumnName = "ASSESSMENTGRADINGID")
    private AssessmentGrading assessmentGrading; // khóa ngoại liên kết AssessmentGrading

    @ManyToOne
    @JoinColumn(name = "PUBLISHEDITEMID", referencedColumnName = "ITEMID")
    private Item item; // khóa ngoại liên kết Item
    
    @ManyToOne
    @JoinColumn(name = "PUBLISHEDITEMTEXTID", referencedColumnName = "ITEMTEXTID")
    private ItemText itemText; // Khóa ngoại liên kết ItemText 
    
    @ManyToOne
    @JoinColumn(name = "PUBLISHEDANSWERID", referencedColumnName = "ANSWERID")
    private Answer answer; // Khóa ngoại liên kết Answer 
    
    @Column(name = "AGENTID")
    private String agentId;

    @Column(name = "ANSWERTEXT")
    private String answerText;

    @Column(name = "ISCORRECT")
    private Boolean isCorrect;
    
    @Column(name = "ID")
    private Long id;

    // Constructor đầy đủ tham số
    public ItemGrading(Long itemGradingId, AssessmentGrading assessmentGrading, Item item, 
                       ItemText itemText, Answer answer, String agentId, 
                       String answerText, Boolean isCorrect, Long id) {
        this.itemGradingId = itemGradingId;
        this.assessmentGrading = assessmentGrading;
        this.item = item;
        this.itemText = itemText;
        this.answer = answer;
        this.agentId = agentId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
        this.id = id;
    }

    // Phương thức toString 
    @Override
    public String toString() {
        return "ItemGrading [\n" +
               "itemGradingId=" + itemGradingId + "\n" +
               ", assessmentGrading=" + (assessmentGrading != null ? assessmentGrading.getAssessmentGradingId() : "null") + "\n" +
               ", item=" + (item != null ? item.getItemId() : "null") + "\n" +
               ", itemText=" + (itemText != null ? itemText.getItemTextId() : "null") + "\n" +
               ", answer=" + (answer != null ? answer.getAnswerId() : "null") + "\n" +
               ", agentId='" + agentId + '\'' + "\n" +
               ", answerText='" + answerText + '\'' + "\n" +
               ", isCorrect=" + isCorrect + "\n" +
               ", id=" + isCorrect + "\n" +
               ']';
    }

}
