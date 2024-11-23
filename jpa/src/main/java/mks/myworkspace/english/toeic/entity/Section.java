package mks.myworkspace.english.toeic.entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedsection_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SECTIONID")
    private Long id;

    @Column(name = "ASSESSMENTID")
    private Long assessmentId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SEQUENCE")
    private Integer sequence; // Thứ tự của phần
    
    @Column(name = "DESCRIPTION")
    private String description;

    // Thêm mối quan hệ với Question
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Question> questions; // Danh sách các câu hỏi trong Section
}
