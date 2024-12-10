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
import mks.myworkspace.english.toeic.entity.ItemGrading;

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
			log.debug("Updating existing assessmentGrading with ID: {}", assessmentGrading.getAssessmentGradingId());
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
	
	public Long saveOrUpdate(ItemGrading itemGrading) {
		Long id;
		
		if (itemGrading.getItemGradingId() == null) {
			log.debug("Inserting new grading");
			id = createItemGrading(itemGrading);
		} else {
			log.debug("Updating existing itemGrading with ID: {}", itemGrading.getItemGradingId());
//			updateItemGrading(itemGrading);
			id = itemGrading.getItemGradingId();
		}

		log.debug("Resulting ID after saveOrUpdate: {}", id);
		return id; 
	}
	

	private Long createItemGrading(ItemGrading itemGrading) {
	    Long id;
	    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate0)
	        .withTableName("sam_itemgrading_t")
	        .usingGeneratedKeyColumns("ITEMGRADINGID");

	    Map<String, Object> parameters = new HashMap<>();

	    // Thêm các trường tương ứng từ ItemGrading entity vào map
	    parameters.put("ASSESSMENTGRADINGID", itemGrading.getAssessmentGrading() != null ? itemGrading.getAssessmentGrading().getAssessmentGradingId() : null);
	    parameters.put("PUBLISHEDITEMID", itemGrading.getItem() != null ? itemGrading.getItem().getItemId() : null);
	    parameters.put("PUBLISHEDITEMTEXTID", itemGrading.getItemText() != null ? itemGrading.getItemText().getItemTextId() : null);
	    parameters.put("PUBLISHEDANSWERID", itemGrading.getAnswer() != null ? itemGrading.getAnswer().getAnswerId() : null);
	    parameters.put("AGENTID", itemGrading.getAgentId());
	    parameters.put("ANSWERTEXT", itemGrading.getAnswerText());
	    parameters.put("ISCORRECT", itemGrading.getIsCorrect());
	    parameters.put("ID", itemGrading.getId());

	    // Insert và lấy ID của bản ghi mới
	    id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
	    log.debug("New ID: {}", id); 
	    return id;
	}

}
