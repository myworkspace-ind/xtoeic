package mks.myworkspace.english.toeic.service.impl;

import mks.myworkspace.english.toeic.entity.PublishedAnswer;
import mks.myworkspace.english.toeic.entity.PublishedAssessment;
import mks.myworkspace.english.toeic.entity.PublishedItem;
import mks.myworkspace.english.toeic.entity.PublishedSection;
import mks.myworkspace.english.toeic.model.*;
import mks.myworkspace.english.toeic.repository.PublishedAnswerRepository;
import mks.myworkspace.english.toeic.repository.PublishedAssessmentRepository;
import mks.myworkspace.english.toeic.repository.PublishedItemRepository;
import mks.myworkspace.english.toeic.repository.PublishedSectionRepository;
import mks.myworkspace.english.toeic.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import mks.myworkspace.english.toeic.model.Exam;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestion;
import mks.myworkspace.english.toeic.model.ExamQuestionControl;
import mks.myworkspace.english.toeic.model.ExamSection;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final PublishedAssessmentRepository publishedAssessmentRepository;
    private final PublishedSectionRepository publishedSectionRepository;
    private final PublishedItemRepository publishedItemRepository;
    private final PublishedAnswerRepository publishedAnswerRepository;

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
        PublishedAssessment assessment = publishedAssessmentRepository.findById(id).orElse(null);
        if (Objects.isNull(assessment)) {
            return null;
        }
        return Exam.builder()
            .id(assessment.getId())
            .title(assessment.getTitle())
            .description(assessment.getDescription())
            .timeLimit(assessment.getAccessControl().getTimeLimit())
            .dueDate(assessment.getAccessControl().getDueDate())
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
            control.setQuestions(List.of(ExamQuestion.builder()
                .id(publishedItem.getId())
                .questionNo(quesNo)
                .text(publishedItem.getPublishedItemText().getText())
                .answers(publishedItem.getAnswers().stream()
                    .map(publishedAnswer -> ExamAnswer.builder()
                        .id(publishedAnswer.getId())
                        .itemId(publishedItem.getId())
                        .label(publishedAnswer.getLabel())
                        .text(publishedAnswer.getText())
                        .build())
                    .collect(Collectors.toList()))
                .build()));
            return control;
        } else {
            AtomicReference<Integer> startQuestionNo = new AtomicReference<>(passedQues + 1);
            control.setSectNo(sectNo);
            control.setIsFirst(sectNo == 1);
            control.setIsLast(publishedSections.size() == sectNo);
            control.setQuestions(publishedSection.getPublishedItems().stream()
                .map(publishedItem -> ExamQuestion.builder()
                    .id(publishedItem.getId())
                    .questionNo(startQuestionNo.getAndSet(startQuestionNo.get() + 1))
                    .text(publishedItem.getPublishedItemText().getText())
                    .answers(publishedItem.getAnswers().stream()
                        .map(publishedAnswer -> ExamAnswer.builder()
                            .id(publishedAnswer.getId())
                            .itemId(publishedItem.getId())
                            .label(publishedAnswer.getLabel())
                            .text(publishedAnswer.getText())
                            .build())
                        .collect(Collectors.toList()))
                    .build())
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
    @Transactional
    public Integer submitAnswer(Integer examId, Map<Integer, String> answerForm) {
        Collection<Integer> answerIds = answerForm.values().stream().map(Integer::parseInt).collect(Collectors.toList());
        List<PublishedAnswer> answers = publishedAnswerRepository.findAllByIdIn(answerIds);
        return Math.toIntExact(answers.stream()
            .filter(answer -> Boolean.TRUE.equals(answer.getIsCorrect()))
            .count());
    }

}



//@Service
//public class ExamServiceImpl implements ExamService{
//
//	@Autowired
//	private ExamRepository repo;
//	
//	@Override
//	public ExamRepository getRepo() {
//		return repo;
//	}
//	
//	@Override
//	public List<Exam> getAllExams() {	
//		return repo.findAll();
//	}
//	
//	@Override
//	public List<Exam> getExamsWithETSTitlePrefix() {	
//		return repo.findExamsWithETSTitlePrefix();
//	}
//	
//	@Override
//	public List<Exam> getExamsWithETSTitleAndPracticeType() {	
//		return repo.findExamsWithETSTitleAndPracticeType();
//	}
//	
//	@Override
//	public List<Exam> getExamsWithETSTitleAndExamType() {	
//		return repo.findExamsWithETSTitleAndExamType();
//	}
//	
//	@Override
//	public Optional<Exam> findById(Long id) {
//        return repo.findById(id);
//    }
//	
//	//
//	@Autowired
//	private QuestionRepository repoQuestion;
//	
//	@Override
//	public List<Question> getAllQuestions() {	
//		return repoQuestion.findAll();
//	}
//	
//}
