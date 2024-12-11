package mks.myworkspace.english.toeic.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sam_publishedsection_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedSection {

	@Id
    @Column(name = "SECTIONID")
    private Integer id;

    @Column(name = "ASSESSMENTID")
    private Integer assessmentId;

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

}
