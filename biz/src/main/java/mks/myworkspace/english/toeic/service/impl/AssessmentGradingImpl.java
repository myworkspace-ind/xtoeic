package mks.myworkspace.english.toeic.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;
import mks.myworkspace.english.toeic.service.AssessmentGradingService;

@Service
@Slf4j
public class AssessmentGradingImpl implements AssessmentGradingService {

	@Autowired
	private AssessmentGradingRepository repo;

	@Override
	public AssessmentGradingRepository getRepo() {
		return repo;
	}

//	@Override // t đổi qua dùng hàm dưới rồi nha
//	public <S extends AssessmentGrading> S save(S entity) {
//		return repo.save(entity);
//	} 

	@Override
	public void insertAssessmentGrading(AssessmentGrading grading) {
		repo.save(grading);
	}

	@Transactional
	@Override
	public void insertAssessmentGrading(Long publishedAssessmentId, String agentId, LocalDateTime attemptDate,
	                                     LocalDateTime submittedDate, boolean forGrade, int status, boolean isLate, boolean hasAutoSubmissionRun) {
	    try {
	        // Tạo đối tượng AssessmentGrading
	        AssessmentGrading grading = new AssessmentGrading();
	        Exam exam = new Exam();
	        exam.setId(publishedAssessmentId);

	        grading.setExam(exam);
	        grading.setAgentId(agentId);
	        grading.setAttemptDate(attemptDate);
	        grading.setSubmittedDate(submittedDate);
	        grading.setForGrade(forGrade);
	        grading.setStatus(status);
	        grading.setLate(isLate);
	        grading.setHasAutoSubmissionRun(hasAutoSubmissionRun);

	        // Log thông tin đối tượng trước khi lưu
	        log.info("Đối tượng grading sẽ được lưu: {}", grading);

	        // Lưu vào cơ sở dữ liệu
	        repo.save(grading);
	        // Không cần phải gọi flush, Spring sẽ tự động flush khi kết thúc giao dịch.
	        log.info("Dữ liệu đã được lưu thành công.");

	    } catch (Exception e) {
	        log.error("Lỗi khi lưu dữ liệu: ", e);
	        // Bạn có thể rethrow exception nếu muốn giao dịch rollback
	        throw e; 
	    }
	}

}
