package mks.myworkspace.english.toeic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Section;

@Service
public interface SectionService {
//    List<Section> getSectionsByAssessmentId(Long assessmentId);
	
    List<Section> getSectionsByExamId(Long examId); // Đổi tên phương thức và tham số

}

