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
	
	public Long saveOrUpdate(AssessmentGrading grading) {
		Long id;
		
		if (grading.getAssessmentGradingId() == null) {
			log.debug("Inserting new grading");
			id = createAssessmentGrading(grading);
		} else {
			log.debug("Updating existing grading with ID: {}", grading.getAssessmentGradingId());
//			updateAssessmentGrading(grading);
			id = grading.getAssessmentGradingId();
		}

		log.debug("Resulting ID after saveOrUpdate: {}", id);
		return id; 
	}

	private Long createAssessmentGrading(AssessmentGrading grading) {
        Long id;
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate0)
            .withTableName("sam_assessmentgrading_t")
            .usingGeneratedKeyColumns("ASSESSMENTGRADINGID");

        Map<String, Object> parameters = new HashMap<>();
        
        // Thêm các trường tương ứng từ AssessmentGrading entity vào map
        parameters.put("PUBLISHEDASSESSMENTID", grading.getExam() != null ? grading.getExam().getId() : null);
        parameters.put("AGENTID", grading.getAgentId());
        parameters.put("ATTEMPTDATE", grading.getAttemptDate());
        parameters.put("SUBMITTEDDATE", grading.getSubmittedDate());
        parameters.put("FORGRADE", grading.isForGrade());
        parameters.put("STATUS", grading.getStatus());
        parameters.put("ISLATE", grading.isLate());
        parameters.put("HASAUTOSUBMISSIONRUN", grading.isHasAutoSubmissionRun());

        // Insert và lấy ID của bản ghi mới
        id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        log.debug("New ID: {}", id);
        return id;
    }
}
