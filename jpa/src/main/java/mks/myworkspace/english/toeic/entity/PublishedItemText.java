package mks.myworkspace.english.toeic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publisheditemtext_t")
@Immutable
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
