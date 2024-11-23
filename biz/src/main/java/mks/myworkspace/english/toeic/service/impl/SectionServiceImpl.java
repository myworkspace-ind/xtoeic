package mks.myworkspace.english.toeic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mks.myworkspace.english.toeic.entity.Section;
import mks.myworkspace.english.toeic.repository.SectionRepository;
import mks.myworkspace.english.toeic.service.SectionService;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public List<Section> getSectionsByAssessmentId(Long assessmentId) {
        return sectionRepository.findSectionsByAssessmentId(assessmentId);
    }
}
