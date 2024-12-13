package mks.myworkspace.english.toeic.controller;

import java.util.Map;
import java.util.Objects;

import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.AssessmentGrading2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping({"/index", "/"})
    public String home(Model model) {
        return "redirect:exam";
    }

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
        AssessmentGrading2 grading = examService.getCurrentAssesmentGrading(id);
        Integer quesNo = Objects.isNull(grading) || grading.getLastVisitedQuestion() == 0 ? 1: grading.getLastVisitedQuestion();
        Integer secNo = Objects.isNull(grading) || grading.getLastVisitedPart() == 0 ? 1: grading.getLastVisitedPart();
        if ("part".equals(layout)) {
            quesNo = null;
        }
        model.addAttribute("layout", layout);
        model.addAttribute("fragments", quesNo > 1 ?"exam_taking_question":"exam_taking_guide");
        model.addAttribute("exam", examService.getExamById(id));

        ExamQuestionControl control = examService.getQuestions(id, secNo, quesNo);
        model.addAttribute("questionControl", control);
        model.addAttribute("sectNo", secNo);
        model.addAttribute("quesNo", quesNo);
        model.addAttribute("section", examService.getSection(id, secNo));
        model.addAttribute("answerSheet", examService.getExamAnswerSheet(id));
        return "exam-taking";
    }

    @PostMapping(value = "/{id}/taking/stop")
    @ResponseBody
    public String examTakingSave(@PathVariable Integer id, @RequestParam Map<String, String> answerForm) {
        try{
            answerForm.remove("save");
            examService.saveAnswer(id, answerForm);
            return "Save OK";
        }catch (Exception e){
            return "Save not OK";
        }
//        return String.format("redirect:/exam/%s/introduction", id);
    }

    @PostMapping(value = "/{id}/taking/stop", params = "submit")
    public String examTakingSubmit(@PathVariable Integer id, @RequestParam Map<String, String> answerForm, RedirectAttributes redirectAttrs) {
        answerForm.remove("submit");
        Map<String, Object> mapDataGrading = examService.submitAnswer(id, answerForm);
        redirectAttrs.addFlashAttribute("examId", mapDataGrading.get("examId"));
        redirectAttrs.addFlashAttribute("listeningScore", mapDataGrading.get("listeningScore"));
        redirectAttrs.addFlashAttribute("readingScore", mapDataGrading.get("readingScore"));
        redirectAttrs.addFlashAttribute("totalScore", mapDataGrading.get("totalScore"));
        redirectAttrs.addFlashAttribute("gradingId", mapDataGrading.get("gradingId"));
        return String.format("redirect:/exam/%s/result/chart", id);
    }

    @GetMapping("/{id}/result/chart")
    public String examResultChart(@PathVariable Integer id, Model model) {
        return "exam-result-chart";
    }

    @GetMapping(value="{id}/result/{gradingId}")
    public String examTakingDetail(
            @PathVariable Integer id,
            @PathVariable Integer gradingId,
            Model model) {
        Integer quesNo = null;
        model.addAttribute("layout", "part");
        model.addAttribute("exam", examService.getExamById(id));
        ExamQuestionControl control = examService.getQuestions(id, 1, quesNo);
        control.setIsShowBack(true);
        model.addAttribute("questionControl", control);
        model.addAttribute("sectNo", 1);
        model.addAttribute("quesNo", quesNo);
        model.addAttribute("section", examService.getSection(id, 1));
        model.addAttribute("answerSheet", examService.getExamAnswerSheetResult(id, gradingId));
        return "exam-result-detail";
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
            @RequestParam(name = "showBack", required = false, defaultValue = "false") Boolean showBack,
            Model model) {
        if ("part".equals(layout)) {
            quesNo = null;
        }
        model.addAttribute("layout", layout);
        model.addAttribute("exam", examService.getExamById(id));
        ExamQuestionControl control = examService.getQuestions(id, sectNo, quesNo);
        control.setIsShowBack(showBack);
        if ("question".equals(layout) && sectNo < control.getSectNo()) {
            sectNo = control.getSectNo();
            quesNo = control.getQuesNo();
            model.addAttribute("sectNo", sectNo);
            model.addAttribute("quesNo", quesNo);
            model.addAttribute("questionControl", control);
            model.addAttribute("section", examService.getSection(id, sectNo));
            return "fragments/exam :: exam_taking_guide";
        }
        sectNo = control.getSectNo();
        quesNo = control.getQuesNo();
        model.addAttribute("sectNo", sectNo);
        model.addAttribute("quesNo", quesNo);
        model.addAttribute("questionControl", control);
        model.addAttribute("section", examService.getSection(id, sectNo));
        return "fragments/exam :: exam_taking_question";
    }

}
