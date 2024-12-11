package mks.myworkspace.english.toeic.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamQuestion {

	private Integer id;

    private String audio;

    private String audioText;

    private String image;

    private String text;

    private Integer questionNo;

    private List<ExamAnswer> answers;

}
