/**
 * Licensed to MKS Group under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * MKS Group licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package mks.myworkspace.english.toeic.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.entity.Item;
import mks.myworkspace.english.toeic.entity.ItemText;
import mks.myworkspace.english.toeic.service.AnswerService;
import mks.myworkspace.english.toeic.service.AssessmentGradingService;
import mks.myworkspace.english.toeic.service.ExamService;
import mks.myworkspace.english.toeic.service.ItemTextService;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class ToeicController extends BaseController {

	/**
	 * This method is called when binding the HTTP parameter to bean (or model).
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// Sample init of Custom Editor

		// Class<List<ItemKine>> collectionType =
		// (Class<List<ItemKine>>)(Class<?>)List.class;
		// PropertyEditor orderNoteEditor = new MotionRuleEditor(collectionType);
		// binder.registerCustomEditor((Class<List<ItemKine>>)(Class<?>)List.class,
		// orderNoteEditor);

	}

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/header-xToeic", method = RequestMethod.GET)
	public ModelAndView displayHeaderXtoeic(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("header-xToeic");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	/**
	 * Call service
	 */
	@Autowired
	private ExamService examService;
	
	@Autowired
    private AnswerService answerService;   
	

    @Autowired
    private ItemTextService itemTextService;  
	
    @Autowired
    private AssessmentGradingService assessmentGradingService; 
	
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    @RequestMapping(value = "/exam-part-1-vovantri", method = RequestMethod.GET)
    public ModelAndView displayExamPart1_vovantri(@RequestParam("id") Long examId, HttpServletRequest request, HttpSession httpSession) {
        
        // Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh NullPointerException)
        Optional<Exam> examOpt = examService.findById(examId);
        
        if (examOpt.isEmpty()) {
            // Nếu không tìm thấy Exam, trả về trang lỗi
            return new ModelAndView("error").addObject("message", "Exam không tồn tại.");
        }
//        assessmentGradingService.deleteByAssessmentGradingId((long)700);

        assessmentGradingService.insertAssessmentGrading();
        Exam exam = examOpt.get(); // Lấy đối tượng Exam nếu tồn tại

        // Tạo mới một bản ghi AssessmentGrading
        AssessmentGrading grading = new AssessmentGrading();
      //  grading.setAssessmentGradingId((long)700);
        grading.setAgentId(getCurrentUserDisplayName()); // Agent hiện tại
        grading.setIslate(0); // Không bị trễ
        grading.setForGrade(0); // Grade mặc định là 0
        grading.setFinalScore(1); // Điểm ban đầu là 0
        grading.setStatus(0); // Trạng thái mặc định
        grading.setHasauToSubmissIOnRun(0); // Không có auto submission
        grading.setExam(exam); // Gán exam vào grading

        // Ghi thông tin grading ra console
        System.out.println("Thông tin AssessmentGrading trước khi lưu:");
        System.out.println("Agent ID: " + grading.getAssessmentGradingId());

        System.out.println("Agent ID: " + grading.getAgentId());
        System.out.println("Is Late: " + grading.getIslate());
        System.out.println("For Grade: " + grading.getForGrade());
        System.out.println("Final Score: " + grading.getFinalScore());
        System.out.println("Status: " + grading.getStatus());
        System.out.println("Has Auto Submission Run: " + grading.getHasauToSubmissIOnRun());
        if (grading.getExam() != null) {
            System.out.println("Exam ID: " + grading.getExam().getId());
            System.out.println("Exam Title: " + grading.getExam().getTitle());
        } else {
            System.out.println("Exam: null");
        }

        // Lưu vào cơ sở dữ liệu
        assessmentGradingService.save(grading);
        // Ghi thông tin grading ra console sau khi lưu (nếu grading được cập nhật)
        System.out.println("Thông tin AssessmentGrading sau khi lưu:");
        System.out.println("AssessmentGrading ID: " + grading.getAssessmentGradingId());
        System.out.println("Agent ID: " + grading.getAgentId());
        System.out.println("Is Late: " + grading.getIslate());
        System.out.println("For Grade: " + grading.getForGrade());
        System.out.println("Final Score: " + grading.getFinalScore());
        System.out.println("Status: " + grading.getStatus());
        System.out.println("Has Auto Submission Run: " + grading.getHasauToSubmissIOnRun());
        if (grading.getExam() != null) {
            System.out.println("Exam ID: " + grading.getExam().getId());
            System.out.println("Exam Title: " + grading.getExam().getTitle());
        } else {
            System.out.println("Exam: null");
        }



        ModelAndView mav = new ModelAndView("exam-part-1-vovantri");
        initSession(request, httpSession);

        mav.addObject("currentSiteId", getCurrentSiteId());
        mav.addObject("userDisplayName", getCurrentUserDisplayName());

        // Lấy thông tin các câu hỏi của phần 1 của kỳ thi
        List<Object[]> examPart1Details = itemTextService.getItemAndItemTextByExamIDAndPartTitle(examId, "Part1");

        // Khởi tạo danh sách chứa thông tin các câu hỏi
        List<Map<String, Object>> questionDetailsList = new ArrayList<>();

        // Lặp qua tất cả các dòng trong examPart1Details
        for (Object[] row : examPart1Details) {
            Item item = (Item) row[0]; // Cột 0 là Item
            ItemText itemText = (ItemText) row[1]; // Cột 1 là ItemText

            // Lấy text từ itemText
            String text = itemText.getText();
            System.out.println("Item Text: " + text);

            // Tách thủ công image URL và audio URL từ chuỗi
            String imageUrl = extractUrl(text, "image");
            String audioUrl = extractUrl(text, "audio");

            // Tạo một map chứa thông tin câu hỏi
            Map<String, Object> questionDetail = new HashMap<>();
            questionDetail.put("item", item); // Item (câu hỏi)
            questionDetail.put("itemText", itemText); // ItemText (text)
            questionDetail.put("imageUrl", imageUrl); // Image URL
            questionDetail.put("audioUrl", audioUrl); // Audio URL

            // Lấy danh sách câu trả lời cho câu hỏi này
            List<Answer> answers = answerService.getAnswersByItemTextId(itemText.getItemTextId());
            questionDetail.put("answers", answers);

            // Thêm vào danh sách
            questionDetailsList.add(questionDetail);
        }

        // Thêm toàn bộ thông tin câu hỏi vào model
        mav.addObject("questionDetailsList", questionDetailsList);
        mav.addObject("exam", exam); // Đưa thông tin exam vào model

        return mav;
    }

 
	private String extractUrl(String text, String key) {
	    // Biểu thức chính quy tìm URL (cả URL đầy đủ và URL tương đối)
	    String regex = "\""+ key + "\":\\s*\"([^\"]+)\"";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
 
	    if (matcher.find()) {
	        String url = matcher.group(1);  // group(1) là phần bắt được trong dấu ngoặc của biểu thức chính quy
  
	        return url;
	    }
	    return "";   
	}
 
	@RequestMapping(value = "/list-of-exam", method = RequestMethod.GET)
	public ModelAndView displayListOfExam(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("list-of-exam");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		List<Exam> exams = examService.getExamsWithETSTitlePrefix();

		mav.addObject("exams", exams);

		for (Exam exam : exams) {
			log.debug("Thông tin đề thi: {}", exam);
		}

		return mav;
	}

	@RequestMapping(value = "/exam-introduction", method = RequestMethod.GET)
	public ModelAndView displayExamIntroduction(@RequestParam("id") Long ExamId, HttpServletRequest request,
			HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-introduction");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		// Truy vấn dữ liệu từ cơ sở dữ liệu
		Optional<Exam> examOpt = examService.findById(ExamId);

		// Check if the exam exists and add to model
		examOpt.ifPresentOrElse(exam -> {
			mav.addObject("exam", exam);
		}, () -> {
			mav.addObject("errorMessage", "Exam not found.");
		});

		return mav;
	}

	@RequestMapping(value = "/home-xToeic-screen", method = RequestMethod.GET)
	public ModelAndView displayHomexToeicScreen(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("home-xToeic-screen");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-and-listening-explanations", method = RequestMethod.GET)
	public ModelAndView displayExamAndListeningExplanations(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-and-listening-explanations");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/post-exam-results", method = RequestMethod.GET)
	public ModelAndView displayPostExamResultsn(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("post-exam-results");
		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-screen", method = RequestMethod.GET)
	public ModelAndView displayExamScreen(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-screen");
		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-1", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart1(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-1");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-1", method = RequestMethod.GET)
	public ModelAndView displayExamPart1(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-1");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-2", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart2(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-2");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-2", method = RequestMethod.GET)
	public ModelAndView displayExamPart2(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-2");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-3", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart3(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-3");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-3", method = RequestMethod.GET)
	public ModelAndView displayExamPart3(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-3");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-4", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart4(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-4");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-4", method = RequestMethod.GET)
	public ModelAndView displayExamPart4(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-4");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-5", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart5(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-5");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-5", method = RequestMethod.GET)
	public ModelAndView displayExamPart5(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-5");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-6", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart6(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-6");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-6", method = RequestMethod.GET)
	public ModelAndView displayExamPart6(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-6");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-guide-part-7", method = RequestMethod.GET)
	public ModelAndView displayExamGuidePart7(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-guide-part-7");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/exam-part-7", method = RequestMethod.GET)
	public ModelAndView displayExamPart7(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-7");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/warning-when-submitting", method = RequestMethod.GET)
	public ModelAndView displayWarningWhenSubmitting(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("warning-when-submitting");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView displayTest(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("test");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public ModelAndView displayTest2(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("fragments/headerXToeic");

		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}

}
