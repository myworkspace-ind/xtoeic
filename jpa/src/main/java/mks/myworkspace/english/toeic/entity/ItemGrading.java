package mks.myworkspace.english.toeic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_itemgrading_t")
@Getter
@Setter
@NoArgsConstructor
public class ItemGrading implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id; // system field

    @Column(name = "ITEMID")
    private Long itemId; // Foreign key referencing Item entity

    @Column(name = "SCORE")
    private Double score;

    @Column(name = "GRADE")
    private String grade;

    @Override
    public String toString() {
        return "ItemGrading [id=" + id + ", itemId=" + itemId + ", score=" + score + ", grade=" + grade + "]";
    }
}
