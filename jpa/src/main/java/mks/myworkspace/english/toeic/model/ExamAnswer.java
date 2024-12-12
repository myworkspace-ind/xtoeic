package mks.myworkspace.english.toeic.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamAnswer {

    private Integer id;

    private Integer itemId;

    private String text;

    private String label;

    private Boolean isCorrect;

    private Double score;

    private Double discount;

    private Double partialCredit;

    private Boolean isChecked;

}
