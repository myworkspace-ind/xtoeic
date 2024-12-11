package mks.myworkspace.english.toeic.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExamResult {

    private Integer id;
    
    private Integer assessmentId;

    private Date attemptDate;

    private Date submittedDate;

    private Double finalScore;

}
