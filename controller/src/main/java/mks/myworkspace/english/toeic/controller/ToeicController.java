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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Handles requests for the application home page.
 */
@Controller
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
	
	@RequestMapping(value = "/list-of-exam", method = RequestMethod.GET)
	public ModelAndView displayListOfExam(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("list-of-exam");

		initSession(request, httpSession);
		
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}
	
	@RequestMapping(value = "/exam-introduction", method = RequestMethod.GET)
	public ModelAndView displayExamIntroduction(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("exam-introduction");

		initSession(request, httpSession);
		
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

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
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView displayTest(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("test");

		initSession(request, httpSession);
		
		mav.addObject("currentSiteId", getCurrentSiteId());
		mav.addObject("userDisplayName", getCurrentUserDisplayName());

		return mav;
	}
}