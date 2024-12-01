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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import mks.myworkspace.english.toeic.service.ItemService;
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
    private ItemService itemService;  
	
	@Autowired
    private ItemTextService itemTextService;  
	
	@Autowired
    private AnswerService answerService;   
	
    @Autowired
    private AssessmentGradingService assessmentGradingService; 
	
    @RequestMapping(value = "/start-exam", method = RequestMethod.POST)
    public String startExam(HttpServletRequest request, HttpSession httpSession) {
    	// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh NullPointerException)
    	Long examId = Long.parseLong(request.getParameter("examId"));
        Optional<Exam> examOpt = examService.findById(examId); 
//        if (examOpt.isEmpty()) { 
//        	return "redirect:/exam-part-1-vovantri?id=" + examId;;
//        } 
        Exam exam = examOpt.get(); 
        
        AssessmentGrading grading = new AssessmentGrading(); 
        //Do ASSESSMENTGRADINGID tự tăng nên cứ để null  
        grading.setExam(exam);  
        grading.setAgentId(getCurrentUserEid()); 
        grading.setForGrade(false);  // Bấm start là false, Bấm submit là true
        grading.setStatus(0);    // Bấm start là 0, Bấm submit là 1 
        grading.setLate(false);   
        grading.setHasAutoSubmissionRun(false); 
        
        LocalDateTime currentDateTime = LocalDateTime.now();  
        grading.setAttemptDate(currentDateTime);  
        grading.setSubmittedDate(currentDateTime); 
        
        System.out.println("Trước khi lưu: " + grading.toString());
        AssessmentGrading gradingSaved =  assessmentGradingService.saveOrUpdate(grading);
        System.out.println("Sau khi lưu: " + gradingSaved.toString());
    	 
        // Lưu ID của grading vào session để sử dụng sau này khi load câu hỏi
        httpSession.setAttribute("assessmentGradingId", grading.getAssessmentGradingId());

        // Chuyển hướng đến trang câu hỏi
        return "redirect:/exam-part-1-vovantri?examId=" + examId;
    }
    
    @RequestMapping(value = "/exam-part-1-vovantri", method = RequestMethod.GET)
    public ModelAndView displayExamPart1_vovantri(@RequestParam("examId") Long examId, HttpServletRequest request, HttpSession httpSession) {
        
//    	Long assessmentGradingId = (Long) httpSession.getAttribute("assessmentGradingId");
//        if (assessmentGradingId == null) {
//            return new ModelAndView("error").addObject("message", "Bạn cần bắt đầu kỳ thi trước.");
//        }
    		
        // Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh NullPointerException)
        Optional<Exam> examOpt = examService.findById(examId); 
        if (examOpt.isEmpty()) { 
            return new ModelAndView("error").addObject("message", "Exam không tồn tại.");
        } 
        Exam exam = examOpt.get(); 
          
        ModelAndView mav = new ModelAndView("exam-part-1-vovantri");
        
        initSession(request, httpSession); 
        mav.addObject("currentSiteId", getCurrentSiteId());
        mav.addObject("userDisplayName", getCurrentUserDisplayName());

        // Lấy thông tin các câu hỏi của Part 1 của kỳ thi
        List<Item> itemList = itemService.getItemByExamIDAndPartTitle(examId, "Part1");
        List<ItemText> itemTextList = itemTextService.getItemTextByExamIDAndPartTitle(examId, "Part1");
        
        // Khởi tạo danh sách thông tin các câu hỏi
        List<Map<String, Object>> questionDetailsList = new ArrayList<>();
        
        int size = Math.min(itemList.size(), itemTextList.size()); // Chọn kích thước nhỏ nhất để tránh lỗi IndexOutOfBoundsException
        for (int i = 0; i < size; i++) {
            Item item = itemList.get(i);
            ItemText itemText = itemTextList.get(i);
            
            String imageUrl = extractUrl(itemText.getText(), "image");
            String audioUrl = extractUrl(itemText.getText(), "audio");
             
            // Tạo một map chứa thông tin câu hỏi
            Map<String, Object> questionDetail = new HashMap<>();
            questionDetail.put("item", item); // Item (câu hỏi)
            questionDetail.put("itemText", itemText); // ItemText (text)
            questionDetail.put("imageUrl", imageUrl); // Image URL
            questionDetail.put("audioUrl", audioUrl); // Audio URL

            // Lấy danh sách câu trả lời cho câu hỏi này
            List<Answer> answers = answerService.getAnswersByItemTextId(itemText.getItemTextId());
            questionDetail.put("answers", answers);

            // Thêm vào danh sách thông tin câu hỏi 
            questionDetailsList.add(questionDetail);
        } 
     
        // Thêm toàn bộ thông tin câu hỏi vào model
        mav.addObject("exam", exam);  
        mav.addObject("questionDetailsList", questionDetailsList);

        return mav;
    }
    
    /*
    @RequestMapping(value = "/exam-part-1-vovantri", method = RequestMethod.GET)
    public ModelAndView displayExamPart1_vovantri(@RequestParam("id") Long examId, HttpServletRequest request, HttpSession httpSession) {
        
        // Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh NullPointerException)
        Optional<Exam> examOpt = examService.findById(examId); 
        if (examOpt.isEmpty()) { 
            return new ModelAndView("error").addObject("message", "Exam không tồn tại.");
        } 
        Exam exam = examOpt.get(); 
        
        // Tạo mới một bản ghi AssessmentGrading dựa theo constructor này
//      public AssessmentGrading(Long assessmentGradingId, Exam exam, String agentId, LocalDateTime attemptDate,
//    			LocalDateTime submittedDate, int forGrade, int status, int isLate, int hasAutoSubmissionRun)  
        
        AssessmentGrading grading = new AssessmentGrading(); 
        //grading.setAssessmentGradingId(0L); //Do Id tự tăng nên gán gì cũng được 
        grading.setExam(exam);  
        grading.setAgentId(getCurrentUserEid()); 
        grading.setForGrade(false);  // Bấm start là false, Bấm submit là true
        grading.setStatus(0);    // Bấm start là 0, Bấm submit là 1 
        grading.setLate(false);   
        grading.setHasAutoSubmissionRun(false); 
        
        LocalDateTime currentDateTime = LocalDateTime.now();  
        grading.setAttemptDate(currentDateTime);  
        grading.setSubmittedDate(currentDateTime); 
        
        System.out.println("Trước khi lưu: " + grading.toString());
        AssessmentGrading gradingSaved =  assessmentGradingService.saveOrUpdate(grading);
        System.out.println("Sau khi lưu: " + gradingSaved.toString());
        
         
        ModelAndView mav = new ModelAndView("exam-part-1-vovantri");
        initSession(request, httpSession);

        mav.addObject("currentSiteId", getCurrentSiteId());
        mav.addObject("userDisplayName", getCurrentUserDisplayName());

        // Lấy thông tin các câu hỏi của Part 1 của kỳ thi
        List<Item> itemList = itemService.getItemByExamIDAndPartTitle(examId, "Part1");
        List<ItemText> itemTextList = itemTextService.getItemTextByExamIDAndPartTitle(examId, "Part1");
        
        // Khởi tạo danh sách thông tin các câu hỏi
        List<Map<String, Object>> questionDetailsList = new ArrayList<>();
        
        int size = Math.min(itemList.size(), itemTextList.size()); // Chọn kích thước nhỏ nhất để tránh lỗi IndexOutOfBoundsException
        for (int i = 0; i < size; i++) {
            Item item = itemList.get(i);
            ItemText itemText = itemTextList.get(i);
            
            String imageUrl = extractUrl(itemText.getText(), "image");
            String audioUrl = extractUrl(itemText.getText(), "audio");
             
            // Tạo một map chứa thông tin câu hỏi
            Map<String, Object> questionDetail = new HashMap<>();
            questionDetail.put("item", item); // Item (câu hỏi)
            questionDetail.put("itemText", itemText); // ItemText (text)
            questionDetail.put("imageUrl", imageUrl); // Image URL
            questionDetail.put("audioUrl", audioUrl); // Audio URL

            // Lấy danh sách câu trả lời cho câu hỏi này
            List<Answer> answers = answerService.getAnswersByItemTextId(itemText.getItemTextId());
            questionDetail.put("answers", answers);

            // Thêm vào danh sách thông tin câu hỏi 
            questionDetailsList.add(questionDetail);
        } 
     
        // Thêm toàn bộ thông tin câu hỏi vào model
        mav.addObject("questionDetailsList", questionDetailsList);
        mav.addObject("exam", exam);  

        return mav;
    }
    */

 
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
