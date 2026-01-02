package mks.myworkspace.english.toeic.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publisheditem_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedItem {

    @Id
    @Column(name = "ITEMID")
    private Integer id;

    @Column(name = "ITEMIDSTRING")
    private String idString;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "TRIESALLOWED")
    private Integer triesAllowed;

    @Column(name = "INSTRUCTION")
    private String instruction;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPEID")
    private Integer typeId;

    @Column(name = "GRADE")
    private String grade;

    @Column(name = "SCORE")
    private Double score;

    @Column(name = "DISCOUNT")
    private Double discount;

    @Column(name = "ANSWER_OPTIONS_RICH_COUNT")
    private Integer answerOptionsRichCount;

    @Column(name = "ANSWER_OPTIONS_SIMPLE_OR_RICH")
    private Integer answerOptionsSimpleOrRich;

    @Column(name = "SCORE_DISPLAY_FLAG")
    private Boolean scoreDisplayFlag;

    @Column(name = "MIN_SCORE")
    private Double minScore;

    @Column(name = "HINT")
    private String hint;

    @Column(name = "HASRATIONALE")
    private Boolean hasRationale;

    @Column(name = "PARTIAL_CREDIT_FLAG")
    private Boolean partialCreditFlag;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;

    @Column(name = "LASTMODIFIEDBY")
    private String lastModifiedBy;

    @Column(name = "LASTMODIFIEDDATE")
    private Date lastModifiedDate;

    @Column(name = "HASH")
    private String hash;

    @Column(name = "ITEMHASH")
    private String itemHash;

    @Column(name = "ISEXTRACREDIT")
    private Boolean isExtraCredit;

    @Column(name = "MIN_DURATION")
    private Integer minDuration;

    @Column(name = "MAX_DURATION")
    private Integer maxDuration;

    @OneToOne(mappedBy = "publishedItem", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PublishedItemText publishedItemText;

    @ManyToOne
    @JoinColumn(name = "SECTIONID")
    private PublishedSection publishedSection;

    @OrderBy(value = "sequence")
    @OneToMany(mappedBy = "publishedItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PublishedAnswer> answers;

}
