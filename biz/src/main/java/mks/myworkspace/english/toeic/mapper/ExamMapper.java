package mks.myworkspace.english.toeic.mapper;

import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mks.myworkspace.english.toeic.entity.PublishedItem;
import mks.myworkspace.english.toeic.entity.PublishedItemText;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestion;

public class ExamMapper {

    private ExamMapper() {
    }

    public static ExamQuestion toExamQuestion(Integer quesNo, PublishedItem publishedItem) {
        ExamQuestion examQuestion = ExamQuestion.builder()
            .id(publishedItem.getId())
            .questionNo(quesNo)
            .answers(publishedItem.getAnswers().stream()
                .map(publishedAnswer -> ExamAnswer.builder()
                    .id(publishedAnswer.getId())
                    .itemId(publishedItem.getId())
                    .label(publishedAnswer.getLabel())
                    .text(publishedAnswer.getText())
                    .build())
                .collect(Collectors.toList()))
            .build();
        PublishedItemText publishedItemText = publishedItem.getPublishedItemText();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(publishedItemText.getText());
            examQuestion.setAudio(jsonNode.path("audio").asText());
            examQuestion.setImage(jsonNode.path("image").asText());
        } catch (Exception e) {
            examQuestion.setText(publishedItemText.getText());
        }
        return examQuestion;
    }

}
