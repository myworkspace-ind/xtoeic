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

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mks.myworkspace.english.toeic.entity.Exam;
import mks.myworkspace.english.toeic.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import lombok.extern.slf4j.Slf4j;

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

		//        Class<List<ItemKine>> collectionType = (Class<List<ItemKine>>)(Class<?>)List.class;
		//        PropertyEditor orderNoteEditor = new MotionRuleEditor(collectionType);
		//        binder.registerCustomEditor((Class<List<ItemKine>>)(Class<?>)List.class, orderNoteEditor);

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

	@RequestMapping(value = "/list-of-exam", method = RequestMethod.GET)
	public ModelAndView displayListOfExam(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("list-of-exam");

		initSession(request, httpSession);
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		List<Exam> exams =  examService.getExamsWithETSTitlePrefix();

		mav.addObject("exams", exams);

		for (Exam exam : exams) {
			log.debug("Thông tin đề thi: {}", exam);
		}

		return mav;
	}

	@RequestMapping(value = "/exam-introduction", method = RequestMethod.GET)
	public ModelAndView displayExamIntroduction(@RequestParam("id") Long ExamId, HttpServletRequest request, HttpSession httpSession) {
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
