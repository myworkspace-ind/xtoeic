package mks.myworkspace.english.toeic.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Exam {

    private Integer id;

    private String title;

    private String description;

    private Integer timeLimit;

    private Integer timeElapsed;

    private Date dueDate;

}
