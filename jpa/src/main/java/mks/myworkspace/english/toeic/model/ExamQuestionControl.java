package mks.myworkspace.english.toeic.model;

import lombok.Data;

import java.util.List;

@Data
public class ExamQuestionControl {

    private Boolean isFirst;

    private Boolean isLast;

    private Integer sectNo;

    private Integer quesNo;

    private Boolean isShowBack = Boolean.FALSE;

    private List<ExamQuestion> questions;

}
