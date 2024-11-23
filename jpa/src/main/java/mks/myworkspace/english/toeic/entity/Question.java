package mks.myworkspace.english.toeic.entity;

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

    @Column(name = "SECTIONID")
    private Long sectionId;

    @Column(name = "TEXT")
    private String text;
    
    @Column(name = "SEQUENCE")
    private Integer sequence; // Thứ tự của câu hỏi

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SECTIONID", insertable = false, updatable = false)
    private Section section; // Liên kết đến Section
    
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID", insertable = false, updatable = false)
//    private QuestionText questionText; // Liên kết với QuestionText
}
