package mks.myworkspace.english.toeic.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;

@Repository
@Slf4j
public class AppRepository {
	@Autowired
	@Qualifier("jdbcTemplate0")
	private JdbcTemplate jdbcTemplate0;
	
	@Autowired
	AssessmentGradingRepository assessmentGradingRepository;
	
	public Long saveOrUpdate(AssessmentGrading assessmentGrading) {
		Long id;
		
		if (assessmentGrading.getAssessmentGradingId() == null) {
			log.debug("Inserting new grading");
			id = createAssessmentGrading(assessmentGrading);
		} else {
			log.debug("Updating existing grading with ID: {}", assessmentGrading.getAssessmentGradingId());
//			updateAssessmentGrading(grading);
			id = assessmentGrading.getAssessmentGradingId();
		}

		log.debug("Resulting ID after saveOrUpdate: {}", id);
		return id; 
	}

	private Long createAssessmentGrading(AssessmentGrading assessmentGrading) {
        Long id;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate0)
            .withTableName("sam_assessmentgrading_t")
            .usingGeneratedKeyColumns("ASSESSMENTGRADINGID");

        Map<String, Object> parameters = new HashMap<>();
        
        // Thêm các trường tương ứng từ AssessmentGrading entity vào map
        parameters.put("PUBLISHEDASSESSMENTID", assessmentGrading.getExam() != null ? assessmentGrading.getExam().getId() : null);
        parameters.put("AGENTID", assessmentGrading.getAgentId());
        parameters.put("ATTEMPTDATE", assessmentGrading.getAttemptDate());
        parameters.put("SUBMITTEDDATE", assessmentGrading.getSubmittedDate());
        parameters.put("FORGRADE", assessmentGrading.isForGrade());
        parameters.put("STATUS", assessmentGrading.getStatus());
        parameters.put("ISLATE", assessmentGrading.isLate());
        parameters.put("HASAUTOSUBMISSIONRUN", assessmentGrading.isHasAutoSubmissionRun());

        // Insert và lấy ID của bản ghi mới
        id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        log.debug("New ID: {}", id);
        return id;
    }
}
