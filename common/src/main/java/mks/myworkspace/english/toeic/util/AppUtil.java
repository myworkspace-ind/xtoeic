package mks.myworkspace.english.toeic.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import lombok.extern.slf4j.Slf4j;
import mks.myworkspace.english.toeic.model.HeaderMetaData;
import mks.myworkspace.english.toeic.model.QuestionData;


/**
 * @author ThachLN
 */
@Slf4j
public class AppUtil {

    public static String[] convert(JsonArray jsonQuestion) {
        int len = (jsonQuestion != null) ? jsonQuestion.size() : 0;
        String[] items = new String[len];

        JsonElement jsonElement;
        for (int i = 0; i < len; i++) {
            jsonElement = jsonQuestion.get(i);
            items[i] = (jsonElement instanceof JsonNull) ? null : jsonElement.getAsString();
        }

        return items;
    }
    
    public static QuestionData parseQuestion(String[] questionItems, HeaderMetaData hmt) {
        QuestionData questionData;
        int index;
        // Check valid parameter
//        if (CommonUtil.isNNNE(questionItems)) {
//            if (questionItems.length > ANSWER_IDX) {
//                // Valid
//            } else {
//                return null;
//            }
//        } else {
//            return null;
//        }
        
        questionData = new QuestionData();
        // Get Question
        index = hmt.getIndexQuestion();
        String question = (0 <= index) && (index < questionItems.length) ? questionItems[index] : null;
        questionData.setQuestion(question);;
        
        // Get Question type
        index = hmt.getIndexQuestionType();
        String questionType = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        questionData.setQuestionType(questionType);
        
        // Get Score
        index = hmt.getIndexScore();
        String strScore = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        // Parse score
        Double score = 1.0;
        if ((strScore != null) && (!strScore.isEmpty())) {
            try {
                Double.parseDouble(strScore);
            } catch (NumberFormatException nfEx) {
                log.warn("The score is invalid. Set its default 1.0");
            }
        }
        questionData.setScore(score);
        
        // Get not randomize
        index = hmt.getIndexIsNotRandom();
        String strIsNotRandomize = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        boolean isNotRandomize= ((strIsNotRandomize !=null) && (strIsNotRandomize.length() > 0)) ? true: false;
        questionData.setRandomize(!isNotRandomize);
        
        // Get correct answer
        index = hmt.getIndexCorrectAnswer();
        String correctAnswer = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        questionData.setCorrectAnswer(correctAnswer);
        
        // Get answer options
        int indexAnswerStart = hmt.getIndexAnswerStart();
        int indexAnswerEnd = hmt.getIndexAnswerEnd();
        List<String> answers = new ArrayList<String>(); 
        for (int i = indexAnswerStart; i <= indexAnswerEnd; i++) {
            if (i < questionItems.length) {
                answers.add(questionItems[i]);
            } else {
                // log.warn("Data is invalid at column " + i);
            }
        }

        questionData.setAnswers(answers);
        
        // Get answer feedback
        int indexFeedbackStart = hmt.getIndexFeedbackStart();
        if (indexFeedbackStart > -1) {
            int indexFeedbackEnd = hmt.getIndexFeedbackEnd();
            List<String> feedbacks = new ArrayList<String>(); 
            for (int i = indexFeedbackStart; i <= indexFeedbackEnd; i++) {
                if (i < questionItems.length) {
                    feedbacks.add(questionItems[i]);
                } else {
                    // log.warn("Data is invalid at column " + i);
                }
            }

            questionData.setFeedbacks(feedbacks);
        }
        
        // Get correct feedback
        index = hmt.getIndexCorrectAnswerFeedback();
        String correctFeedback = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        questionData.setCorrectFeedback(correctFeedback);
        
        // Get incorrect feedback
        index = hmt.getIndexInCorrectAnswerFeedback();
        String correctInFeedback = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        questionData.setIncorrectFeedback(correctInFeedback);
        
        // Get objective
        index = hmt.getIndexObjective();
        String objective = (0 <= index) && (index < questionItems.length) ? questionItems[index]: null;
        questionData.setObjective(objective);

        return questionData;
    }

}
