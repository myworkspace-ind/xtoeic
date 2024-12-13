package mks.myworkspace.english.toeic.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mks.myworkspace.english.toeic.entity.PublishedAnswer;
import mks.myworkspace.english.toeic.entity.PublishedItem;
import mks.myworkspace.english.toeic.entity.PublishedItemText;
import mks.myworkspace.english.toeic.model.ExamAnswer;
import mks.myworkspace.english.toeic.model.ExamQuestion;

import java.util.List;
import java.util.stream.Collectors;

public class ExamMapper {

    private ExamMapper() {
    }

    public final static String AUDIO_TEXT_LABEL = "E";
    public static ExamQuestion toExamQuestion(Integer quesNo, PublishedItem publishedItem, List<PublishedAnswer> publishedAnswers) {
        ExamQuestion examQuestion = ExamQuestion.builder()
            .id(publishedItem.getId())
            .questionNo(quesNo)
            .answers(publishedAnswers.stream().filter(pa -> !AUDIO_TEXT_LABEL.equals(pa.getLabel()))
                .map(publishedAnswer -> ExamAnswer.builder()
                    .id(publishedAnswer.getId())
                    .itemId(publishedItem.getId())
                    .label(publishedAnswer.getLabel())
                    .text(publishedAnswer.getText())
                    .build())
                .collect(Collectors.toList()))
            .audioText(publishedAnswers.stream().filter(pa -> AUDIO_TEXT_LABEL.equals(pa.getLabel())).findAny().orElse(new PublishedAnswer()).getText())
            .build();
        PublishedItemText publishedItemText = publishedItem.getPublishedItemText();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(publishedItemText.getText());
            examQuestion.setAudio(jsonNode.path("audio").asText());
            examQuestion.setImage(jsonNode.path("image").asText());
        } catch (JsonProcessingException e) {
            examQuestion.setText(publishedItemText.getText());
        }
        return examQuestion;
    }

}
