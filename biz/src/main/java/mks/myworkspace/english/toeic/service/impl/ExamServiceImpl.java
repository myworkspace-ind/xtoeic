package mks.myworkspace.english.toeic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import mks.myworkspace.english.toeic.entity.AssessmentGrading;
import mks.myworkspace.english.toeic.entity.ItemGrading;
import mks.myworkspace.english.toeic.entity.PublishedAccessControl;
import mks.myworkspace.english.toeic.entity.PublishedAnswer;
import mks.myworkspace.english.toeic.entity.PublishedAssessment;
import mks.myworkspace.english.toeic.entity.PublishedItem;
import mks.myworkspace.english.toeic.entity.PublishedSection;
import mks.myworkspace.english.toeic.mapper.ExamMapper;
import mks.myworkspace.english.toeic.model.Exam;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamResult;
import mks.myworkspace.english.toeic.model.ExamSection;
import mks.myworkspace.english.toeic.repository.AssessmentGradingRepository;
import mks.myworkspace.english.toeic.repository.ItemGradingRepository;
import mks.myworkspace.english.toeic.repository.PublishedAnswerRepository;
import mks.myworkspace.english.toeic.repository.PublishedAssessmentRepository;
import mks.myworkspace.english.toeic.service.ExamService;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final PublishedAssessmentRepository publishedAssessmentRepository;
    private final PublishedAnswerRepository publishedAnswerRepository;
    private final AssessmentGradingRepository assessmentGradingRepository;
    private final ItemGradingRepository itemGradingRepository;

    // Default agent id -> change to dynamic on login user
    private static final String AGENT_ID = "70a9eec6-9663-40ad-aa1c-120dbfc9665d";

    @Override
    @Transactional
    public List<Exam> getExams() {
        List<PublishedAssessment> assessments = publishedAssessmentRepository.findAll();
        return assessments.stream()
            .map(assessment -> Exam.builder()
                .id(assessment.getId())
                .title(assessment.getTitle())
                .description(assessment.getDescription())
                .timeLimit(assessment.getAccessControl().getTimeLimit())
                .dueDate(assessment.getAccessControl().getDueDate())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Exam getExamById(Integer id) {
        PublishedAssessment assessment = publishedAssessmentRepository.findById(id)
                .orElse(null);
        if (Objects.isNull(assessment)) {
            return null;
        }
        PublishedAccessControl accessControl = assessment.getAccessControl();
        AssessmentGrading assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(AGENT_ID, id, 0);
        return Exam.builder()
            .id(assessment.getId())
            .title(assessment.getTitle())
            .description(assessment.getDescription())
            .timeLimit(accessControl.getTimeLimit() != null ? accessControl.getTimeLimit() : 0)
            .dueDate(accessControl.getDueDate())
            .timeElapsed(assessmentGrading != null ? assessmentGrading.getTimeElapsed() : 0)
            .build();
    }

    @Override
    @Transactional
    public ExamSection getSection(Integer examId, Integer sectNo) {
        PublishedAssessment publishedAssessment = publishedAssessmentRepository.findById(examId)
            .orElse(null);
        if (Objects.isNull(publishedAssessment)) {
            return null;
        }
        List<PublishedSection> publishedSections = publishedAssessment.getSections();
        if (publishedSections.size() < sectNo
            || CollectionUtils.isEmpty(publishedSections)) {
            return null;
        }
        PublishedSection publishedSection = publishedSections.get(sectNo - 1);
        return ExamSection.builder()
            .id(publishedSection.getId())
            .assessmentId(publishedSection.getPublishedAssessment().getId())
            .sequence(publishedSection.getSequence())
            .title(publishedSection.getTitle())
            .description(publishedSection.getDescription())
            .build();
    }

    @Override
    @Transactional
    public void attemptExam(Integer examId) {
        AssessmentGrading assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
            AGENT_ID, examId, 0);
        if (assessmentGrading == null) {
            assessmentGrading = AssessmentGrading.builder()
                .assessmentId(examId)
                .agentId(AGENT_ID)
                .isLate(false)
                .forGrade(false)
                .totalOverrideScore(0D)
                .status(0)
                .attemptDate(new Date())
                .timeElapsed(0)
                .lastVisitedPart(0)
                .lastVisitedQuestion(0)
                .hasAutoSubmissionRun(false)
                .build();
            assessmentGradingRepository.save(assessmentGrading);
        }
    }

    @Override
    @Transactional
    public ExamQuestionControl getQuestions(Integer examId, Integer sectNo, Integer quesNo) {
        ExamQuestionControl control = new ExamQuestionControl();
        control.setIsFirst(false);
        control.setIsLast(false);
        control.setSectNo(sectNo);
        control.setSectNo(quesNo);
        PublishedAssessment publishedAssessment = publishedAssessmentRepository.findById(examId)
            .orElse(null);
        if (Objects.isNull(publishedAssessment)) {
            control.setQuestions(new ArrayList<>());
            return control;
        }
        List<PublishedSection> publishedSections = publishedAssessment.getSections();
        if (publishedSections.size() < sectNo
            || CollectionUtils.isEmpty(publishedSections)) {
            control.setQuestions(new ArrayList<>());
            return control;
        }
        PublishedSection publishedSection = publishedSections.get(sectNo - 1);
        int passedQues = 0;
        for (int i = 0; i < sectNo - 1; i++) {
            passedQues += publishedSections.get(i).getPublishedItems().size();
        }

        if (Objects.nonNull(quesNo)) {
            if (publishedSection.getPublishedItems().size() < quesNo - passedQues
                && publishedSections.size() > sectNo) {
                sectNo++;
                passedQues += publishedSection.getPublishedItems().size();
                publishedSection = publishedSections.get(sectNo - 1);
            }
            if (sectNo > 1 && quesNo == passedQues) {
                sectNo--;
                publishedSection = publishedSections.get(sectNo - 1);
                passedQues -= publishedSection.getPublishedItems().size();
            }
            control.setSectNo(sectNo);
            control.setQuesNo(quesNo);
            control.setIsFirst(sectNo == 1 && quesNo == 1);
            control.setIsLast(publishedSections.size() == sectNo && publishedSection.getPublishedItems().size() == quesNo);
            PublishedItem publishedItem = publishedSection.getPublishedItems().get(quesNo - passedQues - 1);
            control.setQuestions(List.of(ExamMapper.toExamQuestion(quesNo, publishedItem)));
            return control;
        } else {
            AtomicReference<Integer> startQuestionNo = new AtomicReference<>(passedQues + 1);
            control.setSectNo(sectNo);
            control.setIsFirst(sectNo == 1);
            control.setIsLast(publishedSections.size() == sectNo);
            control.setQuestions(publishedSection.getPublishedItems().stream()
                .map(publishedItem -> ExamMapper.toExamQuestion(startQuestionNo.getAndSet(startQuestionNo.get() + 1), publishedItem))
                .collect(Collectors.toList()));
            return control;
        }
    }

    @Override
    @Transactional
    public Map<Integer, List<ExamAnswer>> getExamAnswerSheet(Integer examId) {
        // 1st solution
        PublishedAssessment assessment = publishedAssessmentRepository.findById(examId)
            .orElse(null);
        if (assessment == null) return null;
        AssessmentGrading assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(AGENT_ID, examId, 0);
        List<Integer> checkedAnswerIds;
        if (assessmentGrading != null) {
            List<ItemGrading> itemGradings = itemGradingRepository.findAllByAssessmentGradingId(assessmentGrading.getId());
            checkedAnswerIds = itemGradings.stream()
                .map(ItemGrading::getPublishedAnswerId)
                .collect(Collectors.toList());
        } else {
            checkedAnswerIds = new ArrayList<>();
        }
        List<PublishedSection> sections = assessment.getSections();
        int questionNo = 0;
        Map<Integer, List<ExamAnswer>> answerSheet = new HashMap<>();
        for (PublishedSection section : sections) {
            List<PublishedItem> items = section.getPublishedItems();
            for (PublishedItem item : items) {
                answerSheet.put(++questionNo,
                    item.getAnswers().stream()
                        .map(publishedAnswer -> ExamAnswer.builder()
                            .id(publishedAnswer.getId())
                            .itemId(item.getId())
                            .label(publishedAnswer.getLabel())
                            .text(publishedAnswer.getText())
                            .isChecked(checkedAnswerIds.contains(publishedAnswer.getId()))
                            .build())
                        .collect(Collectors.toList())
                );
            }
        }

        // 2nd solution
//        List<ExamAnswer> answers = publishedAnswerRepository.findAllByExamId(examId);
//        int questionNo = 0;
//        Map<Integer, List<ExamAnswer>> answerSheet = new HashMap<>();
//        int secNo = answers.get(0).getSectionNo();
//        int itemNo = answers.get(0).getSectionNo();
//        List<ExamAnswer> splitAnswers = new ArrayList<>();
//        for (ExamAnswer answer : answers) {
//            if (answer.getSectionNo() != secNo || answer.getItemNo() != itemNo) {
//                questionNo++;
//                answerSheet.put(questionNo, splitAnswers);
//                splitAnswers = new ArrayList<>();
//                secNo = answer.getSectionNo();
//                itemNo = answer.getItemNo();
//            }
//            splitAnswers.add(answer);
//        }
//        questionNo++;
//        answerSheet.put(questionNo, splitAnswers);

        return answerSheet;
    }

    @Override
    public void saveAnswer(Integer examId, Map<String, String> answerForm) {
        Integer timeElapsed = Integer.valueOf(answerForm.get("-1"));
        answerForm.remove("-1");
        PublishedAssessment assessment = publishedAssessmentRepository.findById(examId)
            .orElse(null);
        if (assessment == null) return;
        List<PublishedSection> sections = assessment.getSections();
        List<ItemGrading> itemGradings = new ArrayList<>();
        int questionNo = 0;
        List<Integer> answerIds = answerForm.values().stream()
            .map(Integer::parseInt).collect(Collectors.toList());
        AssessmentGrading assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
            AGENT_ID, examId, 0);
        if (assessmentGrading == null) return;
        assessmentGrading.setTotalAutoScore(this.autoScore(answerIds));
        assessmentGrading.setTimeElapsed(timeElapsed);
        assessmentGradingRepository.saveAndFlush(assessmentGrading);
        for (PublishedSection section : sections) {
            List<PublishedItem> items = section.getPublishedItems();
            for (PublishedItem item : items) {
                questionNo++;
                if (answerForm.containsKey(String.valueOf(questionNo))) {
                    ItemGrading itemGrading = itemGradingRepository.findByAssessmentGradingIdAndPublishedItemId(assessmentGrading.getId(), item.getId());
                    Integer answerId = Integer.parseInt(answerForm.get(String.valueOf(questionNo)));
                    PublishedAnswer answer = item.getAnswers().stream()
                        .filter(publishedAnswer -> publishedAnswer.getId().equals(answerId))
                        .findFirst().orElse(null);
                    if (answer == null) continue;
                    if (itemGrading == null) {
                        itemGrading = ItemGrading.builder()
                            .assessmentGradingId(assessmentGrading.getId())
                            .publishedItemId(item.getId())
                            .publishedItemTextId(item.getPublishedItemText().getId())
                            .agentId(AGENT_ID)
                            .publishedAnswerId(answer.getId())
                            .answerText(String.format("%s-%s", questionNo, answer.getLabel()))
                            .isCorrect(answer.getIsCorrect())
                            .build();
                    } else {
                        itemGrading.setPublishedAnswerId(answer.getId());
                        itemGrading.setAnswerText(String.format("%s-%s", questionNo, answer.getLabel()));
                        itemGrading.setIsCorrect(answer.getIsCorrect());
                        itemGradings.add(itemGrading);
                    }
                    itemGradings.add(itemGrading);
                }
            }
        }
        if (!CollectionUtils.isEmpty(itemGradings)) {
            itemGradingRepository.saveAll(itemGradings);
        }
    }

    @Override
    @Transactional
    public Double submitAnswer(Integer examId, Map<String, String> answerForm) {
        answerForm.remove("-1");
        List<Integer> answerIds = answerForm.values().stream()
            .map(Integer::parseInt).collect(Collectors.toList());
        return autoScore(answerIds);
    }

    @Override
    @Transactional
    public Page<ExamResult> getPagingResult(Integer examId, Pageable pageable) {
        Page<AssessmentGrading> assessmentGradingPage = assessmentGradingRepository.findAllByAgentIdAndAssessmentId(AGENT_ID, examId, pageable);
        return assessmentGradingPage.map(assessmentGrading -> ExamResult.builder()
            .id(assessmentGrading.getId())
            .attemptDate(assessmentGrading.getAttemptDate())
            .submittedDate(assessmentGrading.getSubmittedDate())
            .finalScore(assessmentGrading.getFinalScore())
            .build());
    }

    private Double autoScore(List<Integer> answerIds) {
        List<PublishedAnswer> answers = publishedAnswerRepository.findAllByIdIn(answerIds);
        return answers.stream()
            .filter(answer -> Boolean.TRUE.equals(answer.getIsCorrect()))
            .map(PublishedAnswer::getScore)
            .reduce(0D, Double::sum);
    }

}
