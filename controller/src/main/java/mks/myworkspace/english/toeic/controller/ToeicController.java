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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.entity.Answer;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.entity.Item;
import mks.myworkspace.english.toeic.entity.ItemGrading;
import mks.myworkspace.english.toeic.entity.ItemText;
import mks.myworkspace.english.toeic.service.AnswerService;
import mks.myworkspace.english.toeic.service.AssessmentGradingService;
import mks.myworkspace.english.toeic.service.ExamService;
import mks.myworkspace.english.toeic.service.ItemGradingService;
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

	@Autowired
	private ItemGradingService itemGradingService;

	@RequestMapping(value = "/start-part-5", method = RequestMethod.POST)
	public String startPart3_NTT(@RequestParam("examId") Long examId, HttpSession httpSession) {
		System.out.println("Exam ID received: " + examId);

		// Tìm Exam theo ID
		Exam exam = examService.findById(examId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid exam ID: " + examId));

		AssessmentGrading assessmentGrading = new AssessmentGrading();
		assessmentGrading.setExam(exam);
		assessmentGrading.setAgentId(getCurrentUserEmail());
		assessmentGrading.setForGrade(false);
		assessmentGrading.setStatus(0);
		assessmentGrading.setLate(false);
		assessmentGrading.setHasAutoSubmissionRun(false);

		LocalDateTime currentDateTime = LocalDateTime.now();
		assessmentGrading.setAttemptDate(currentDateTime);
		assessmentGrading.setSubmittedDate(currentDateTime);

		System.out.println("Trước khi lưu: " + assessmentGrading.toString());
		AssessmentGrading assessmentGradingSaved = assessmentGradingService.saveOrUpdate(assessmentGrading);
		System.out.println("Sau khi lưu: " + assessmentGradingSaved.toString());

		httpSession.setAttribute("assessmentGrading", assessmentGrading);
		return "redirect:/exam-part-5-NguyenTuanThanh?examId=" + examId;
	}

	@RequestMapping(value = "/exam-part-5-NguyenTuanThanh", method = RequestMethod.GET)
	public ModelAndView displayExamPart5_NguyenTuanThanh(@RequestParam("examId") Long examId, HttpServletRequest request,
			HttpSession httpSession) {

		AssessmentGrading assessmentGrading = (AssessmentGrading) httpSession.getAttribute("assessmentGrading");
		if (assessmentGrading == null) {
			return new ModelAndView("error").addObject("message", "Bạn cần bắt đầu kỳ thi trước.");
		}

		// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh
		// NullPointerException)
		Optional<Exam> examOpt = examService.findById(examId);
		if (examOpt.isEmpty()) {
			return new ModelAndView("error").addObject("message", "Exam không tồn tại.");

		}
		Exam exam = examOpt.get();
		System.out.println(exam.toString());

		ModelAndView mav = new ModelAndView("exam-part5-NguyenTuanThanh");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		// Lấy thông tin các câu hỏi của Part 1 của kỳ thi
		List<Item> itemList = itemService.getItemByExamIDAndPartTitle(examId, "Part5");
		List<ItemText> itemTextList = itemTextService.getItemTextByExamIDAndPartTitle(examId, "Part5");

		// Khởi tạo danh sách thông tin các câu hỏi
		List<Map<String, Object>> questionDetailsList = new ArrayList<>();

		int size = Math.min(itemList.size(), itemTextList.size()); // Chọn kích thước nhỏ nhất để tránh lỗi
		
		// IndexOutOfBoundsException
		for (int i = 0; i < size; i++) {
			Item item = itemList.get(i);
			ItemText itemText = itemTextList.get(i);
			String question = itemText.getText();
			// Tạo một map chứa thông tin câu hỏi
			Map<String, Object> questionDetail = new HashMap<>();
			questionDetail.put("item", item); // Item (câu hỏi)
			questionDetail.put("itemText", itemText); // ItemText (text)
			questionDetail.put("question", question);
			// Lấy danh sách câu trả lời cho câu hỏi này
			List<Answer> answers = answerService.getAnswersByItemTextId(itemText.getItemTextId());
			questionDetail.put("answers", answers);

			// Thêm vào danh sách thông tin câu hỏi
			questionDetailsList.add(questionDetail);

			StringBuilder feedbackStringBuilder = new StringBuilder();
			List<String> answerFeedback = answerService.findFeedbackTextsByItemTextId(itemText.getItemTextId());
			String feedbackString = String.join("\n", answerFeedback);
			feedbackStringBuilder.append(feedbackString).append("\n");
			questionDetail.put("feedback", feedbackStringBuilder);

			System.out.println(feedbackStringBuilder);

		}

		// Thêm toàn bộ thông tin câu hỏi vào model
		mav.addObject("assessmentGrading", assessmentGrading);
		mav.addObject("exam", exam);
		mav.addObject("questionDetailsList", questionDetailsList);

		return mav;
	}

	@RequestMapping(value = "/start-part-2", method = RequestMethod.POST)
	public String startPart2(HttpServletRequest request, HttpSession httpSession) {
		// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh
		// NullPointerException)
		Long examId = Long.parseLong(request.getParameter("examId"));
		Optional<Exam> examOpt = examService.findById(examId);
		Exam exam = examOpt.get();

		AssessmentGrading assessmentGrading = new AssessmentGrading();
//        assessmentGrading.setAssessmentGradingId(714L); 
		// Do ASSESSMENTGRADINGID tự tăng nên cứ để null
		assessmentGrading.setExam(exam);
		assessmentGrading.setAgentId(getCurrentUserEid());
		assessmentGrading.setForGrade(false);
		assessmentGrading.setStatus(0);
		assessmentGrading.setLate(false);
		assessmentGrading.setHasAutoSubmissionRun(false);

		LocalDateTime currentDateTime = LocalDateTime.now();
		assessmentGrading.setAttemptDate(currentDateTime);
		assessmentGrading.setSubmittedDate(currentDateTime);

		System.out.println("Trước khi lưu: " + assessmentGrading.toString());
		AssessmentGrading assessmentGradingSaved = assessmentGradingService.saveOrUpdate(assessmentGrading);
		System.out.println("Sau khi lưu: " + assessmentGradingSaved.toString());

		httpSession.setAttribute("assessmentGrading", assessmentGrading);
		// Chuyển hướng đến trang câu hỏi
		return "redirect:/exam-part-2-LyHung?examId=" + examId;
	}

	@RequestMapping(value = "/exam-part-2-LyHung", method = RequestMethod.GET)
	public ModelAndView displayExamPart2_LyHung(@RequestParam("examId") Long examId, HttpServletRequest request,
			HttpSession httpSession) {

		AssessmentGrading assessmentGrading = (AssessmentGrading) httpSession.getAttribute("assessmentGrading");
		if (assessmentGrading == null) {
			return new ModelAndView("error").addObject("message", "Bạn cần bắt đầu kỳ thi trước.");
		}

		// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh
		// NullPointerException)
		Optional<Exam> examOpt = examService.findById(examId);
		if (examOpt.isEmpty()) {
			return new ModelAndView("error").addObject("message", "Exam không tồn tại.");

		}
		Exam exam = examOpt.get();
		System.out.println(exam.toString());

		ModelAndView mav = new ModelAndView("exam-part-2-LyHung");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		// Lấy thông tin các câu hỏi của Part 1 của kỳ thi
		List<Item> itemList = itemService.getItemByExamIDAndPartTitle(examId, "Part2");
		List<ItemText> itemTextList = itemTextService.getItemTextByExamIDAndPartTitle(examId, "Part2");

		// Khởi tạo danh sách thông tin các câu hỏi
		List<Map<String, Object>> questionDetailsList = new ArrayList<>();

		int size = Math.min(itemList.size(), itemTextList.size()); // Chọn kích thước nhỏ nhất để tránh lỗi
																	// IndexOutOfBoundsException
		for (int i = 0; i < size; i++) {
			Item item = itemList.get(i);
			ItemText itemText = itemTextList.get(i);

			String audioUrl = extractUrl(itemText.getText(), "audio");

			// Tạo một map chứa thông tin câu hỏi
			Map<String, Object> questionDetail = new HashMap<>();
			questionDetail.put("item", item); // Item (câu hỏi)
			questionDetail.put("itemText", itemText); // ItemText (text)
			questionDetail.put("audioUrl", audioUrl); // Audio URL

			// Lấy danh sách câu trả lời cho câu hỏi này
			List<Answer> answers = answerService.getAnswersByItemTextId(itemText.getItemTextId());
			questionDetail.put("answers", answers);

			// Thêm vào danh sách thông tin câu hỏi
			questionDetailsList.add(questionDetail);

			StringBuilder feedbackStringBuilder = new StringBuilder();
			List<String> answerFeedback = answerService.findFeedbackTextsByItemTextId(itemText.getItemTextId());
			String feedbackString = String.join("\n", answerFeedback);
			feedbackStringBuilder.append(feedbackString).append("\n");
			questionDetail.put("feedback", feedbackStringBuilder);

			System.out.println(feedbackStringBuilder);

		}

		// Thêm toàn bộ thông tin câu hỏi vào model
		mav.addObject("assessmentGrading", assessmentGrading);
		mav.addObject("exam", exam);
		mav.addObject("questionDetailsList", questionDetailsList);

		return mav;
	}

	@RequestMapping(value = "/start-part-1", method = RequestMethod.POST)
	public String startPart1(HttpServletRequest request, HttpSession httpSession) {
		// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh
		// NullPointerException)
		Long examId = Long.parseLong(request.getParameter("examId"));
		Optional<Exam> examOpt = examService.findById(examId);
//        if (examOpt.isEmpty()) { 
//        	return "redirect:/exam-part-1-vovantri?id=" + examId;;
//        } 
		Exam exam = examOpt.get();

		AssessmentGrading assessmentGrading = new AssessmentGrading();
//        assessmentGrading.setAssessmentGradingId(714L); 
		// Do ASSESSMENTGRADINGID tự tăng nên cứ để null
		assessmentGrading.setExam(exam);
		assessmentGrading.setAgentId(getCurrentUserEid());
		assessmentGrading.setForGrade(false);
		assessmentGrading.setStatus(0);
		assessmentGrading.setLate(false);
		assessmentGrading.setHasAutoSubmissionRun(false);

		LocalDateTime currentDateTime = LocalDateTime.now();
		assessmentGrading.setAttemptDate(currentDateTime);
		assessmentGrading.setSubmittedDate(currentDateTime);

		System.out.println("Trước khi lưu: " + assessmentGrading.toString());
		AssessmentGrading assessmentGradingSaved = assessmentGradingService.saveOrUpdate(assessmentGrading);
		System.out.println("Sau khi lưu: " + assessmentGradingSaved.toString());

		httpSession.setAttribute("assessmentGrading", assessmentGrading);
		// Chuyển hướng đến trang câu hỏi
		return "redirect:/exam-part-1-vovantri?examId=" + examId;
	}

	@RequestMapping(value = "/exam-part-1-vovantri", method = RequestMethod.GET)
	public ModelAndView displayExamPart1_vovantri(@RequestParam("examId") Long examId, HttpServletRequest request,
			HttpSession httpSession) {

		AssessmentGrading assessmentGrading = (AssessmentGrading) httpSession.getAttribute("assessmentGrading");
		if (assessmentGrading == null) {
			return new ModelAndView("error").addObject("message", "Bạn cần bắt đầu kỳ thi trước.");
		}

		// Tìm Exam theo ID từ cơ sở dữ liệu (sử dụng Optional để tránh
		// NullPointerException)
		Optional<Exam> examOpt = examService.findById(examId);
		if (examOpt.isEmpty()) {
			return new ModelAndView("error").addObject("message", "Exam không tồn tại.");

		}
		Exam exam = examOpt.get();
		System.out.println(exam.toString());

		ModelAndView mav = new ModelAndView("exam-part-1-vovantri");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		// Lấy thông tin các câu hỏi của Part 1 của kỳ thi
		List<Item> itemList = itemService.getItemByExamIDAndPartTitle(examId, "Part1");
		List<ItemText> itemTextList = itemTextService.getItemTextByExamIDAndPartTitle(examId, "Part1");

		// Khởi tạo danh sách thông tin các câu hỏi
		List<Map<String, Object>> questionDetailsList = new ArrayList<>();

		int size = Math.min(itemList.size(), itemTextList.size()); // Chọn kích thước nhỏ nhất để tránh lỗi
																	// IndexOutOfBoundsException
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

			StringBuilder feedbackStringBuilder = new StringBuilder();
			List<String> answerFeedback = answerService.findFeedbackTextsByItemTextId(itemText.getItemTextId());
			String feedbackString = String.join("\n", answerFeedback);
			feedbackStringBuilder.append(feedbackString).append("\n");
			questionDetail.put("feedback", feedbackStringBuilder);

			System.out.println(feedbackStringBuilder);

		}

		// Thêm toàn bộ thông tin câu hỏi vào model
		mav.addObject("assessmentGrading", assessmentGrading);
		mav.addObject("exam", exam);
		mav.addObject("questionDetailsList", questionDetailsList);

		return mav;
	}

//	@RequestMapping(value = "/exam-part-1-vovantri", method = RequestMethod.GET)
//	public ModelAndView displayExamPart1_vovantri(@RequestParam("id") Long examId, HttpServletRequest request, HttpSession httpSession) {
//	    ModelAndView mav = new ModelAndView("exam-part-1-vovantri");
//	    initSession(request, httpSession);
//
//	    mav.addObject("currentSiteId", getCurrentSiteId());
//	    mav.addObject("userDisplayName", getCurrentUserDisplayName());
//
//	    List<Object[]> examPart1Details = examService.findAllExamPart1Details(examId);
//	    //List<Object[]> examPart1Details = examService.getExamPart1Details(examId);

//	    // Lấy dòng đầu tiên
//	    Object[] firstRow = examPart1Details.get(0);
//
//	    // Truy cập cột 0 và cột 1
//	    Item item = (Item) firstRow[0];
//	    ItemText itemText = (ItemText) firstRow[1];
//	    System.out.println("Item Sequence: " + item.getSequence()); 
//
//	    // Lấy text từ itemText
//	    String text = itemText.getText();
//	    System.out.println("Item Text: " + text);
//
//	    // Tách thủ công image URL và audio URL từ chuỗi
//	    String imageUrl = extractUrl(text, "image");
//	    String audioUrl = extractUrl(text, "audio");
//	    
//	    mav.addObject("imageUrl", imageUrl);
//	    mav.addObject("audioUrl", audioUrl);
//
//	    System.out.println("Image URL: " + imageUrl);
//	    System.out.println("Audio URL: " + audioUrl);
//
//	    // Ghi các câu a,b,c,d
//	    List<Answer> answers = new ArrayList<>();
//	    for (Object[] row : examPart1Details) {
//	        Answer answer = (Answer) row[2];
//	        answers.add(answer);   
//
//	        System.out.println("-------------------------");
//	        System.out.println("Answer Sequence: " + answer.getSequence());
//	        System.out.println("Answer Label: " + answer.getLabel());
//	        System.out.println("Answer Text: " + answer.getText());
//	        System.out.println("Is Correct: " + answer.getIsCorrect());
//	        System.out.println("Score: " + answer.getScore());
//	        System.out.println("-------------------------");
//	    }
//	    
//	    mav.addObject("answers", answers);  
//	    Optional<Exam> examOpt = examService.findById(examId);
//
//	    examOpt.ifPresentOrElse(
//	        exam -> mav.addObject("exam", exam),
//	        () -> mav.addObject("errorMessage", "Exam not found.")
//	    );
//
//	    return mav;
//	}

	// Hàm tách URL từ chuỗi JSON thủ công
	private String extractUrl(String text, String key) {
		// Biểu thức chính quy tìm URL (cả URL đầy đủ và URL tương đối)
		String regex = "\"" + key + "\":\\s*\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			String url = matcher.group(1); // group(1) là phần bắt được trong dấu ngoặc của biểu thức chính quy

			return url;
		}
		return "";
	}

	@RequestMapping(value = "/saveAnswerOfUser", method = RequestMethod.POST)
	@ResponseBody
	public String saveAnswerOfUser(HttpServletRequest request) {
		try {
			// Lấy thông tin từ request
			Long assessmentGradingId = Long.parseLong(request.getParameter("assessmentGradingId"));
			Long itemId = Long.parseLong(request.getParameter("itemId"));
			Long itemTextId = Long.parseLong(request.getParameter("itemTextId"));
			Long answerId = Long.parseLong(request.getParameter("answerId"));
			String answerText = request.getParameter("answerText");
			Long id = 0L;

			// Tìm các thực thể liên quan
			Optional<AssessmentGrading> assessmentGradingOpt = assessmentGradingService.findById(assessmentGradingId);
			Optional<Item> itemOpt = itemService.findById(itemId);
			Optional<ItemText> itemTextOpt = itemTextService.findById(itemTextId);
			Optional<Answer> answerOpt = answerService.findById(answerId);

			if (assessmentGradingOpt.isPresent() && itemOpt.isPresent() && itemTextOpt.isPresent()
					&& answerOpt.isPresent()) {
				AssessmentGrading assessmentGrading = assessmentGradingOpt.get();
				Item item = itemOpt.get();
				ItemText itemText = itemTextOpt.get();
				Answer answer = answerOpt.get();

				Boolean isCorrect = answer.getIsCorrect();
				String agentId = getCurrentUserEid();

				// Tạo và lưu thực thể ItemGrading
				ItemGrading itemGrading = new ItemGrading(null, assessmentGrading, item, itemText, answer, agentId,
						answerText, isCorrect, id);

				// Lưu itemGrading vào database

				System.out.println("Trước khi lưu: " + itemGrading.toString());
				ItemGrading itemGradingSaved = itemGradingService.saveOrUpdate(itemGrading);
				System.out.println("Sau khi lưu: " + itemGradingSaved.toString());

				return "success";
			} else {
				return "fail"; // Không tìm thấy một trong các thực thể
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Lỗi trong quá trình xử lý
		}
	}

	// show part 2 - created by Huu Huy

	@RequestMapping(value = "/exam-part-2-huuhuy", method = RequestMethod.GET)
	public ModelAndView displayExamPart2_huuhuy(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-part-2-huuhuy");
		initSession(request, httpSession);

		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
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
