package mks.myworkspace.english.toeic.entity;

import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publisheditem_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "SECTIONID", referencedColumnName = "SECTIONID") // Liên kết khóa ngoại
    private Section section;

//    @Column(name = "SECTIONID")
//    private Long sectionId;

    @Column(name = "TEXT")
    private String text;
    
    @Column(name = "SEQUENCE")
    private Integer sequence; // Thứ tự của câu hỏi
 
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Answer> answers; // Danh sách các đáp án liên quan đến câu hỏi
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID") // Khớp ITEMID trong bảng QuestionText
    private QuestionText questionText; // Liên kết với QuestionText
    
    @Transient // Không lưu trong cơ sở dữ liệu
    private Integer globalSequence;
}
