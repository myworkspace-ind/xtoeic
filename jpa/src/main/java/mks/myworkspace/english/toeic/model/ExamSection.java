package mks.myworkspace.english.toeic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
