package mks.myworkspace.english.toeic.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamTakingOption;
import mks.myworkspace.english.toeic.service.ExamService2;

@Controller
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService2 examService;

    @GetMapping("")
    public String exam(Model model) {
        model.addAttribute("examList", examService.getExams());
        model.addAttribute("examResultPage", Page.empty(PageRequest.of(0, 1)));
        return "exam";
    }

    @GetMapping("/{id}/introduction")
    public String examIntroduction(@PathVariable Integer id, Model model) {
        ExamTakingOption option = ExamTakingOption.builder()
            .layout("question")
            .build();

        model.addAttribute("option", option);
        model.addAttribute("exam", examService.getExamById(id));
        return "exam-introduction2";
    }

    @PostMapping("/{id}/taking/start")
    public String examTakingStart(@PathVariable Integer id, @ModelAttribute ExamTakingOption option) {
        examService.attemptExam(id);
        return String.format("redirect:/exam/%s/taking?layout=%s",
            id, option.getLayout());
    }

    @GetMapping("/{id}/taking")
    public String examTaking(
        @PathVariable Integer id,
        @RequestParam(name = "layout") String layout,
        Model model) {
        Integer quesNo = 1;
        if ("part".equals(layout)) {
            quesNo = null;
        }
        model.addAttribute("layout", layout);
        model.addAttribute("exam", examService.getExamById(id));
        ExamQuestionControl control = examService.getQuestions(id, 1, quesNo);
        model.addAttribute("questionControl", control);
        model.addAttribute("sectNo", 1);
        model.addAttribute("quesNo", quesNo);
        model.addAttribute("section", examService.getSection(id, 1));
        model.addAttribute("answerSheet", examService.getExamAnswerSheet(id));
        return "exam-taking";
    }

    @PostMapping(value = "/{id}/taking/stop", params = "save")
    public String examTakingSave(@PathVariable Integer id, @RequestParam Map<String, String> answerForm) {
        answerForm.remove("save");
        examService.saveAnswer(id, answerForm);
        return String.format("redirect:/exam/%s/introduction", id);
    }

    @PostMapping(value = "/{id}/taking/stop", params = "submit")
    public String examTakingSubmit(@PathVariable Integer id, @RequestParam Map<String, String> answerForm, RedirectAttributes redirectAttrs) {
        answerForm.remove("submit");
        Double totalScore = examService.submitAnswer(id, answerForm);
        redirectAttrs.addFlashAttribute("totalScore", totalScore);
        return String.format("redirect:/exam/%s/result/chart", id);
    }

    @GetMapping("/{id}/result/chart")
    public String examResultChart(@PathVariable Integer id, Model model) {
        return "exam-result-chart";
    }

    @PostMapping(value="{id}/result")
    public String examTakingResult(
        @PathVariable Integer id,
        Model model,
        Pageable pageable) {
        model.addAttribute("examResultPage", examService.getPagingResult(id, pageable));
        return "fragments/exam :: exam_result";
    }

    @PostMapping(value="{id}/taking/questions")
    public String examTakingQuestion(
        @PathVariable Integer id,
        @RequestParam(name = "layout") String layout,
        @RequestParam(name = "sectNo") Integer sectNo,
        @RequestParam(name = "quesNo", required = false) Integer quesNo,
        Model model) {
        if ("part".equals(layout)) {
            quesNo = null;
        }
        model.addAttribute("layout", layout);
        model.addAttribute("exam", examService.getExamById(id));
        ExamQuestionControl control = examService.getQuestions(id, sectNo, quesNo);
        sectNo = control.getSectNo();
        quesNo = control.getQuesNo();
        model.addAttribute("questionControl", control);
        model.addAttribute("sectNo", sectNo);
        model.addAttribute("quesNo", quesNo);
        model.addAttribute("section", examService.getSection(id, sectNo));
        return "fragments/exam :: exam_taking_question";
    }

}
