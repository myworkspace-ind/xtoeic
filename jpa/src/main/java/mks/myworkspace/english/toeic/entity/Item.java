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
@Table(name = "sam_publisheditem_t", uniqueConstraints = @UniqueConstraint(columnNames = "ITEMID"))
@Getter
@Setter
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMID")
    private Long itemId; // system field 
    
    @Column(name = "SEQUENCE")
	private Integer sequence;
    
    @ManyToOne
	@JoinColumn(name = "SECTIONID", referencedColumnName = "SECTIONID")
	private Part part;
    
    @OneToMany(mappedBy = "item")
	private List<ItemText> itemTexts;
         
    public Item(Long itemId, Integer sequence, Part part, List<ItemText> itemTexts) {
        this.itemId = itemId;
        this.sequence = sequence;
        this.part = part;
        this.itemTexts = itemTexts;
    }
    
    @Override
    public String toString() {
        return "Item [" +
               "itemId=" + itemId + "\n" +
               ", sequence=" + sequence + "\n" +
               ", part=" + (part != null ? part.getSectionId() : "null") + "\n" + // Hiển thị id của Part
               ", partTitle=" + (part != null ? part.getTitle() : "null") + "\n" + // Hiển thị title của Part
               ", itemTextsCount=" + (itemTexts != null ? itemTexts.size() : 0) + "\n" + // Hiển thị số lượng ItemText (thường là 1)
               ']';
    }


}
