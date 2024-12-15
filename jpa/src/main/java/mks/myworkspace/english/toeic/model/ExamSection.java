package mks.myworkspace.english.toeic.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamSection {

    private Integer id;

    private Integer assessmentId;

    private Integer sequence;

    private String part;

    private String title;

    private String description;

}
