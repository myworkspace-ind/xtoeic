package mks.myworkspace.english.toeic.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publishedanswer_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWERID")
    private Long id;

    @Column(name = "ITEMTEXTID")
    private Long itemTextId;

//    @Column(name = "ITEMID")
//    private Long itemId;
    
    @ManyToOne
    @JoinColumn(name = "ITEMID", referencedColumnName = "ITEMID")
    private Question question; // Khóa ngoại liên kết với Question
    
    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "TEXT")
    private String text; // Nội dung đáp án

    @Column(name = "LABEL")
    private String label; // A, B, C, D

    @Column(name = "ISCORRECT")
    private Boolean isCorrect;
}
