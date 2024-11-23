package mks.myworkspace.english.toeic.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sam_publisheditemtext_t")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEMTEXTID")
    private Long id;

    @Column(name = "ITEMID")
    private Long itemId;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "TEXT")
    private String text; // Dữ liệu JSON từ cột TEXT
}
