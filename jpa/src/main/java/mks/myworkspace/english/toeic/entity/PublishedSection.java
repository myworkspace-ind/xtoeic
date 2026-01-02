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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedsection_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedSection {

    @Id
    @Column(name = "SECTIONID")
    private Integer id;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "TYPEID")
    private Integer typeId;

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

    @ManyToOne
    @JoinColumn(name = "ASSESSMENTID", referencedColumnName = "ID")
    private PublishedAssessment publishedAssessment;

    @OrderBy(value = "sequence")
    @OneToMany(mappedBy = "publishedSection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PublishedItem> publishedItems;

}
