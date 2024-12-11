package mks.myworkspace.english.toeic.model;

import java.util.List;

import lombok.Data;

@Data
public class ExamQuestionControl {

    private Boolean isFirst;

    private Boolean isLast;

    private Integer sectNo;

    private Integer quesNo;
    
    private Boolean isShowBack = Boolean.FALSE;

    private List<ExamQuestion> questions;

}
