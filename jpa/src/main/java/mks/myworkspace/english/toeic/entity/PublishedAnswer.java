package mks.myworkspace.english.toeic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedanswer_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedAnswer {

    @Id
    @Column(name = "ANSWERID")
    private Integer id;

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

    @ManyToOne
    @JoinColumn(name = "ITEMID")
    private PublishedItem publishedItem;

}
