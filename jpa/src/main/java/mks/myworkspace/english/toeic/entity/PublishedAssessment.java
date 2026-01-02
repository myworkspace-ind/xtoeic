package mks.myworkspace.english.toeic.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "sam_publishedassessment_t")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ASSESSMENTID")
    private String assessmentId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TYPEID")
    private Integer typeId;

    @Column(name = "INSTRUCTORNOTIFICATION")
    private Integer instructorNotification;

    @Column(name = "TESTEENOTIFICATION")
    private Integer testeeNotification;

    @Column(name = "MULTIPARTALLOWED")
    private Integer multipartAllowed;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private Date createdDate;

    @Column(name = "LASTMODIFIEDBY")
    private String lastModifiedBy;

    @Column(name = "LASTMODIFIEDDATE")
    private Date lastmodifieddate;

    @Column(name = "LASTNEEDRESUBMITDATE")
    private Date lastNeedResubmitDate;

    @Column(name = "CATEGORYID")
    private Integer categoryId;

    @OneToOne(mappedBy = "publishedAssessment", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PublishedAccessControl accessControl;

    @OrderBy(value = "sequence")
    @OneToMany(mappedBy = "publishedAssessment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PublishedSection> sections;

}