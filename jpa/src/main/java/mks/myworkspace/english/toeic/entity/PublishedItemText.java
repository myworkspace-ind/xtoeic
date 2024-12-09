package mks.myworkspace.english.toeic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sam_publisheditemtext_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedItemText {

    @Id
    @Column(name = "ITEMTEXTID")
    private Integer id;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "REQUIRED_OPTIONS_COUNT")
    private Integer requiredOptionsCount;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ITEMID")
    private PublishedItem publishedItem;

}
