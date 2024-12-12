package mks.myworkspace.english.toeic.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionControl {

    private Boolean isFirst;

    private Boolean isLast;

    private Integer sectNo;

    private Integer quesNo;

    private List<ExamQuestion> questions;

}
