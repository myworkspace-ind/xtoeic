package mks.myworkspace.english.toeic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sam_publishedanswer_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedAnswer {

    @Id
    @Column(name = "ANSWERID")
    private Integer id;

    @Column(name = "ITEMID")
    private Integer itemId;

    @Column(name = "ITEMTEXTID")
    private Integer itemTextId;

    @Column(name = "text")
    private String text;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "label")
    private String label;

    @Column(name = "iscorrect")
    private Boolean isCorrect;

    @Column(name = "GRADE")
    private String grade;

    @Column(name = "score")
    private Double score;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "partial_credit")
    private Double partialCredit;

}
