package mks.myworkspace.english.toeic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sam_publisheditem_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedItem {

    @Id
    @Column(name = "ITEMID")
    private Integer id;

    @Column(name = "SECTIONID")
    private Integer sectionId;

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

}
