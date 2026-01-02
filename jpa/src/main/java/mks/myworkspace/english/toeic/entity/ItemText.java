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

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publisheditemtext_t", uniqueConstraints = @UniqueConstraint(columnNames = "ITEMTEXTID"))
@Immutable
@Getter
@Setter
@NoArgsConstructor
public class ItemText implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMTEXTID")
    private Long itemTextId; // system field

//    @Column(name = "ITEMID")
//    private Long itemId; // Foreign key referencing Item entity
    
    @Column(name = "SEQUENCE")
	private Integer sequence;

    @Column(name = "TEXT", length = 500)
    private String text; 
    
    @ManyToOne
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
	private Item item;
    
    @OneToMany(mappedBy = "itemText")
	private List<Answer> answers;

    public ItemText(Long itemTextId, Integer sequence, String text, Item item, List<Answer> answers) {
        this.itemTextId = itemTextId;
        this.sequence = sequence;
        this.text = text;
        this.item = item;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "ItemText [\n" +
               "itemTextId=" + itemTextId + "\n" +
               ", sequence=" + sequence + "\n" +
               ", text='" + text + '\'' + "\n" +
               ", item=" + (item != null ? item.getItemId() : "null") + "\n" +  // Hiển thị id của Item
               ", answersCount=" + (answers != null ? answers.size() : 0) + "\n" + // Hiển thị số lượng Answer
               ']';
    }


}
