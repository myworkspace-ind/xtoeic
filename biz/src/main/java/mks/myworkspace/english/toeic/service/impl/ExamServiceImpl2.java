package mks.myworkspace.english.toeic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import mks.myworkspace.english.toeic.entity.*;
import mks.myworkspace.english.toeic.enums.HeaderQuestion;
import mks.myworkspace.english.toeic.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import mks.myworkspace.english.toeic.mapper.ExamMapper;
import mks.myworkspace.english.toeic.model.Exam;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamResult;
import mks.myworkspace.english.toeic.model.ExamSection;
import mks.myworkspace.english.toeic.service.ExamService2;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl2 implements ExamService2 {
    private final PublishedAssessmentRepository publishedAssessmentRepository;
    private final PublishedSectionRepository publishedSectionRepository;
    private final PublishedItemRepository publishedItemRepository;
    private final PublishedAnswerRepository publishedAnswerRepository;
    private final AssessmentGradingRepository2 assessmentGradingRepository;
    private final ItemGradingRepository2 itemGradingRepository;

    // Default agent id -> change to dynamic on login user
    private static final String AGENT_ID = "70a9eec6-9663-40ad-aa1c-120dbfc9665d";

    @Override
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
    public Exam getExamById(Integer id) {
        PublishedAssessment assessment = publishedAssessmentRepository.findById(id)
                .orElse(null);
        if (Objects.isNull(assessment)) {
            return null;
        }
        PublishedAccessControl accessControl = assessment.getAccessControl();
        AssessmentGrading2 assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(AGENT_ID, id, 0);
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
    public ExamSection getSection(Integer examId, Integer sectNo) {
        PublishedAssessment publishedAssessment = publishedAssessmentRepository.findById(examId)
                .orElse(null);
        if (Objects.isNull(publishedAssessment)) {
            return null;
        }
        List<PublishedSection> publishedSections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        if (publishedSections.size() < sectNo
                || CollectionUtils.isEmpty(publishedSections)) {
            return null;
        }
        PublishedSection publishedSection = publishedSections.get(sectNo - 1);
        return ExamSection.builder()
                .id(publishedSection.getId())
                .assessmentId(examId)
                .sequence(publishedSection.getSequence())
                .part(HeaderQuestion.valueOf(publishedSection.getTitle()).name())
                .title(HeaderQuestion.valueOf(publishedSection.getTitle()).getTitle())
                .description(HeaderQuestion.valueOf(publishedSection.getTitle()).getDescription())
                .build();
    }

    @Override
    @Transactional(value = "transactionManagerJpa")
    public void attemptExam(Integer examId) {
        AssessmentGrading2 assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
                AGENT_ID, examId, 0);
        if (assessmentGrading == null) {
            assessmentGrading = AssessmentGrading2.builder()
                    .assessmentId(examId)
                    .agentId(AGENT_ID)
                    .isLate(false)
                    .forGrade(false)
                    .totalOverrideScore(0D)
                    .status(0)
                    .submittedDate(new Date())
                    .attemptDate(new Date())
                    .timeElapsed(0)
                    .lastVisitedPart(0)
                    .lastVisitedQuestion(0)
                    .hasAutoSubmissionRun(false)
                    .build();
            assessmentGradingRepository.saveAndFlush(assessmentGrading);
        }
    }

    @Override
    public ExamQuestionControl getQuestions(Integer examId, Integer sectNo, Integer quesNo) {
        ExamQuestionControl control = new ExamQuestionControl();
        control.setIsFirst(false);
        control.setIsLast(false);
        control.setSectNo(sectNo);
        control.setQuesNo(quesNo);

        List<PublishedSection> publishedSections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        if (publishedSections.size() < sectNo
                || CollectionUtils.isEmpty(publishedSections)) {
            control.setQuestions(new ArrayList<>());
            return control;
        }
        PublishedSection publishedSection = publishedSections.get(sectNo - 1);
        List<Integer> passedSectionIds = new ArrayList<>();
        for (int i = 0; i < sectNo - 1; i++) {
            passedSectionIds.add(publishedSections.get(i).getId());
        }

        int passedQues = publishedItemRepository.countAllBySectionIdIn(passedSectionIds);

        if (Objects.nonNull(quesNo)) {
            Integer sectionItemCount = publishedItemRepository.countAllBySectionId(publishedSection.getId());
            if (sectionItemCount < quesNo - passedQues
                    && publishedSections.size() > sectNo) {
                sectNo++;
                passedQues += sectionItemCount;
                publishedSection = publishedSections.get(sectNo - 1);
                sectionItemCount = publishedItemRepository.countAllBySectionId(publishedSection.getId());
            }
            if (sectNo > 1 && quesNo == passedQues) {
                sectNo--;
                publishedSection = publishedSections.get(sectNo - 1);
                sectionItemCount = publishedItemRepository.countAllBySectionId(publishedSection.getId());
                passedQues -= sectionItemCount;
            }
            control.setSectNo(sectNo);
            control.setQuesNo(quesNo);
            control.setIsFirst(sectNo == 1 && quesNo == 1);
            sectionItemCount = publishedItemRepository.countAllBySectionId(publishedSection.getId());
            control.setIsLast(publishedSections.size() == sectNo && Objects.equals(sectionItemCount, quesNo - passedQues));

            List<PublishedItem> publishedItems = publishedItemRepository.findAllBySectionIdOrderBySequence(publishedSection.getId());
            PublishedItem publishedItem = publishedItems.get(quesNo - passedQues - 1);
            control.setQuestions(List.of(ExamMapper.toExamQuestion(
                    quesNo,
                    publishedItem,
                    publishedAnswerRepository.findAllByItemIdOrderBySequence(publishedItem.getId()))));
            return control;
        } else {
            AtomicReference<Integer> startQuestionNo = new AtomicReference<>(passedQues + 1);
            control.setSectNo(sectNo);
            control.setIsFirst(sectNo == 1);
            control.setIsLast(publishedSections.size() == sectNo);
            control.setQuestions(publishedItemRepository.findAllBySectionIdOrderBySequence(publishedSection.getId()).stream()
                    .map(publishedItem -> ExamMapper.toExamQuestion(
                            startQuestionNo.getAndSet(startQuestionNo.get() + 1),
                            publishedItem,
                            publishedAnswerRepository.findAllByItemIdOrderBySequence(publishedItem.getId())))
                    .collect(Collectors.toList()));
            return control;
        }
    }

    @Override
    public Map<Integer, List<ExamAnswer>> getExamAnswerSheet(Integer examId) {
        // 1st solution
        AssessmentGrading2 assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(AGENT_ID, examId, 0);
        List<Integer> checkedAnswerIds;
        if (assessmentGrading != null) {
            List<ItemGrading2> itemGradings = itemGradingRepository.findAllByAssessmentGradingId(assessmentGrading.getId());
            checkedAnswerIds = itemGradings.stream()
                    .map(ItemGrading2::getPublishedAnswerId)
                    .collect(Collectors.toList());
        } else {
            checkedAnswerIds = new ArrayList<>();
        }
        List<PublishedSection> sections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        int questionNo = 0;
        Map<Integer, List<ExamAnswer>> answerSheet = new HashMap<>();
        for (PublishedSection section : sections) {
            List<PublishedItem> items = publishedItemRepository.findAllBySectionIdOrderBySequence(section.getId());
            for (PublishedItem item : items) {
                answerSheet.put(++questionNo,
                        publishedAnswerRepository.findAllByItemIdOrderBySequence(item.getId()).stream()
                                .filter(pa -> !ExamMapper.AUDIO_TEXT_LABEL.equals(pa.getLabel()))
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

        return answerSheet;
    }

    @Override
    public Map<Integer, List<ExamAnswer>> getExamAnswerSheetResult(Integer examId, Integer gradingId) {
        // 1st solution
        AssessmentGrading2 assessmentGrading;
        try {
            assessmentGrading = assessmentGradingRepository.findByAgentIdAndIdAndStatus(AGENT_ID, gradingId, 2);
            if (assessmentGrading == null) return new HashMap<>();
        } catch (Exception e) {
            return new HashMap<>();
        }
        List<ItemGrading2> itemGradings = itemGradingRepository.findAllByAssessmentGradingId(assessmentGrading.getId());
        List<Integer> checkedAnswerIds = itemGradings.stream()
                .map(ItemGrading2::getPublishedAnswerId)
                .collect(Collectors.toList());

        List<PublishedSection> sections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        int questionNo = 0;
        Map<Integer, List<ExamAnswer>> answerSheet = new HashMap<>();
        for (PublishedSection section : sections) {
            List<PublishedItem> items = publishedItemRepository.findAllBySectionIdOrderBySequence(section.getId());
            for (PublishedItem item : items) {
                answerSheet.put(++questionNo,
                        publishedAnswerRepository.findAllByItemIdOrderBySequence(item.getId()).stream()
                                .filter(answer -> !answer.getLabel().equals("E"))
                                .map(publishedAnswer -> ExamAnswer.builder()
                                        .id(publishedAnswer.getId())
                                        .itemId(item.getId())
                                        .label(publishedAnswer.getLabel())
                                        .text(publishedAnswer.getText())
                                        .isCorrect(publishedAnswer.getIsCorrect())
                                        .isChecked(checkedAnswerIds.contains(publishedAnswer.getId()))
                                        .build())
                                .collect(Collectors.toList())
                );
            }
        }

        return answerSheet;
    }

    @Override
    @Transactional(value = "transactionManagerJpa")
    public void saveAnswer(Integer examId, Map<String, String> answerForm) {
        Integer timeElapsed = Integer.valueOf(answerForm.get("timeRemain"));
        answerForm.remove("timeRemain");
        List<PublishedSection> sections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        List<ItemGrading2> itemGradings = new ArrayList<>();
        int questionNo = 0;
        List<Integer> answerIds = answerForm.values().stream()
                .map(Integer::parseInt).collect(Collectors.toList());
        AssessmentGrading2 assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
                AGENT_ID, examId, 0);
        if (assessmentGrading == null) return;
        assessmentGrading.setTotalAutoScore(this.autoScore(answerIds));
        assessmentGrading.setTimeElapsed(timeElapsed);
        assessmentGrading.setLastVisitedQuestion(Integer.parseInt(answerForm.get("currentQuestion")));
        assessmentGrading.setLastVisitedPart(Integer.parseInt(answerForm.get("currentPart")));
        assessmentGradingRepository.saveAndFlush(assessmentGrading);
        for (PublishedSection section : sections) {
            List<PublishedItem> items = publishedItemRepository.findAllBySectionIdOrderBySequence(section.getId());
            for (PublishedItem item : items) {
                questionNo++;
                if (answerForm.containsKey(String.valueOf(questionNo))) {
                    ItemGrading2 itemGrading = itemGradingRepository.findByAssessmentGradingIdAndPublishedItemId(assessmentGrading.getId(), item.getId());
                    Integer answerId = Integer.parseInt(answerForm.get(String.valueOf(questionNo)));
                    PublishedAnswer answer = publishedAnswerRepository.findAllByItemIdOrderBySequence(item.getId()).stream()
                            .filter(publishedAnswer -> publishedAnswer.getId().equals(answerId))
                            .findFirst().orElse(null);
                    if (answer == null) continue;
                    if (itemGrading == null) {
                        itemGrading = ItemGrading2.builder()
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
    @Transactional(value = "transactionManagerJpa")
    public Map<String, Object> submitAnswer(Integer examId, Map<String, String> answerForm) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", "OK");
        Integer timeElapsed = Integer.valueOf(answerForm.get("timeRemain"));
        answerForm.remove("timeRemain");
        PublishedAssessment assessment = publishedAssessmentRepository.findById(examId)
                .orElse(null);
        if (assessment == null) return map;
        List<PublishedSection> sections = publishedSectionRepository.findAllByAssessmentIdOrderBySequence(examId);
        List<ItemGrading2> itemGradings = new ArrayList<>();
        int questionNo = 0;
        List<Integer> answerIds = answerForm.values().stream()
                .map(Integer::parseInt).collect(Collectors.toList());
        AssessmentGrading2 assessmentGrading = assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
                AGENT_ID, examId, 0);

        if (assessmentGrading == null) return map;

        Double autoScore = this.autoScore(answerIds);
        assessmentGrading.setTotalAutoScore(autoScore);
        assessmentGrading.setFinalScore(autoScore);
        assessmentGrading.setTimeElapsed(timeElapsed);
        assessmentGrading.setStatus(2);
        assessmentGrading.setIsAutoSubmitted(timeElapsed >= assessment.getAccessControl().getTimeLimit());
        assessmentGradingRepository.saveAndFlush(assessmentGrading);
        for (PublishedSection section : sections) {
            List<PublishedItem> items = publishedItemRepository.findAllBySectionIdOrderBySequence(section.getId());
            for (PublishedItem item : items) {
                questionNo++;
                if (answerForm.containsKey(String.valueOf(questionNo))) {
                    ItemGrading2 itemGrading = itemGradingRepository.findByAssessmentGradingIdAndPublishedItemId(assessmentGrading.getId(), item.getId());
                    Integer answerId = Integer.parseInt(answerForm.get(String.valueOf(questionNo)));
                    PublishedAnswer answer = publishedAnswerRepository.findAllByItemIdOrderBySequence(item.getId()).stream()
                            .filter(publishedAnswer -> publishedAnswer.getId().equals(answerId))
                            .findFirst().orElse(null);
                    if (answer == null) continue;
                    if (itemGrading == null) {
                        itemGrading = ItemGrading2.builder()
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
        Map<String, Object> mapScore = assessmentGradingRepository.getScoreByPart(assessmentGrading.getId());
        map.put("examId", assessmentGrading.getAssessmentId());
        map.put("listeningScore", mapScore.get("listeningScore"));
        map.put("readingScore", mapScore.get("readingScore"));
        map.put("totalScore", assessmentGrading.getFinalScore());
        map.put("gradingId", assessmentGrading.getId());
        return map;
    }

    @Override
    public Page<ExamResult> getPagingResult(Integer examId, Pageable pageable) {
        Page<AssessmentGrading2> assessmentGradingPage = assessmentGradingRepository.findAllByAgentIdAndAssessmentIdAndStatus(AGENT_ID, examId, 2, pageable);
        return assessmentGradingPage.map(assessmentGrading -> ExamResult.builder()
                .id(assessmentGrading.getId())
                .assessmentId(examId)
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
    @Override
    public AssessmentGrading2 getCurrentAssesmentGrading(Integer examId){
        return assessmentGradingRepository.findByAgentIdAndAssessmentIdAndStatus(
                AGENT_ID, examId, 0);
    }

}
