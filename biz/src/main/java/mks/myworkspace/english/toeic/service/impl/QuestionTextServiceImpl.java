package mks.myworkspace.english.toeic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mks.myworkspace.english.toeic.entity.QuestionText;
import mks.myworkspace.english.toeic.repository.QuestionTextRepository;
import mks.myworkspace.english.toeic.service.QuestionTextService;

@Service
public class QuestionTextServiceImpl implements QuestionTextService {
    @Autowired
    private QuestionTextRepository questionTextRepository;

    @Override
    public QuestionText getQuestionTextByItemId(Long itemId) {
        return questionTextRepository.findByItemId(itemId);
    }

    public String extractTextFromJson(String jsonText) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonText);
            return jsonNode.path("image").asText(); // Lấy thông tin "image" từ JSON
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
