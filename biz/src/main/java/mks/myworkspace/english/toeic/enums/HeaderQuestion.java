package mks.myworkspace.english.toeic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeaderQuestion {
    Part1("Part 1: Picture description",
            "<h3>LISTENING TEST</h3>\n" +
                    "              In the Listening test, you will be asked to demonstrate how well\n" +
                    "              you understand spoken English. The entire Listening test will\n" +
                    "              last approximately 45 minutes. There are four parts, and\n" +
                    "              directions are given for each part. You must mark your answers on\n" +
                    "              the separate answer sheet. Do not write your answers in your test\n" +
                    "              book.\n" +
            "<b>Directions:</b> For each question in this part.you will hear\n" +
            "              four statements about a picture in your test book.When you hear\n" +
            "              the statements,you must select the one statement that best\n" +
            "              describes what are see in the picture.Then find the number of the\n" +
            "              question on your answer sheet and mark your answer.The statements\n" +
            "              will not be printed in your test book and will be spoken only one\n" +
            "              time.<br>"),
    Part2("Part 2: Question and Response","<b>Directions:</b> You will hear a question or statement and\n" +
            "              three responses spoken in English. They will not be printed in\n" +
            "              your test book and will be spoken only one time. Select the best\n" +
            "              response to the question or statement and mark the letter (A),\n" +
            "              (B), or (C) on your answer sheet."),
    Part3("Part 3: Short conversation","<b>Directions:</b> You will hear some conversations between two\n" +
            "              people. You will be asked to answer three questions about what\n" +
            "              the speakers say in each conversation. Select the best response\n" +
            "              to each question and mark the letter (A), (B), (C), or (D) on\n" +
            "              your answer sheet. The conversations will not be printed in your\n" +
            "              test book and will be spoken only one time."),
    Part4("Part 4: Short talk","<b>Directions:</b> You will hear some talks given by a single\n" +
            "              speaker. You will be asked to answer three questions about what\n" +
            "              the speaker says in each talk. Select the best response to each\n" +
            "              question and mark the letter (A), (B), (C), or (D) on your answer\n" +
            "              sheet. The talks will not be printed in your test book and will\n" +
            "              be spoken only one time."),
    Part5("Part 5: Incomplete sentences","<h3>READING TEST</h3>\n" +
            "              <p>In the Reading test, you will read a variety of texts and\n" +
            "                answer serveral different types of reading comprehension\n" +
            "                questions. The entire Reading test will last 75 minutes. There\n" +
            "                are three parts, and directions are given for each part. You are\n" +
            "                encouraged to answer as many questions are possible within the\n" +
            "                time allowed. You must mark your answers on the separate answer\n" +
            "                sheet. Do not write your answers in your test book.</p>\n" +
            "<b>Directions:</b> A word or phrase is missing in each of the\n" +
            "                sentences below. Four answer choices are given below each\n" +
            "                sentence. Select the best answer to complete the sentence. Then\n" +
            "                mark the letter (A), (B), (C) or (D) on your answer sheet."),
    Part6("Part 6: Text completion","<b>Directions:</b> Read the texts that follow. A word or phrase\n" +
            "                is missing in some of the sentences. Four answer choices are\n" +
            "                given below each of the sentences. Select the best answer to\n" +
            "                complete the text. Then mark the letter (A), (B), (C), or (D) on\n" +
            "                your answer sheet."),
    Part7("Part 7: Passages","<b>Directions:</b> In this part, you will read a selection of\n" +
            "                texts, such as magazine and newspaper articles, letters, and\n" +
            "                advertisements. Each text is followed by several questions.\n" +
            "                Select the best answer for each question and mark the letter\n" +
            "                (A), (B), (C), or (D) on your answer sheet."),
    NONE("Part ??: ??","");
    private final String title;

    private final String description;
}
