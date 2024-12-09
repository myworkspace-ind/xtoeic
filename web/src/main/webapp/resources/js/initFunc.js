$(function() {
    // Lấy danh sách Assessment
    $.ajax({
        type: "GET",
        url: "/getAssessmentData", // URL của API trả về dữ liệu
        success: function(data) {
            console.log("Dữ liệu nhận được từ backend: ", data);

            // Giả sử bạn muốn hiển thị các phần và câu hỏi trong phần
            data.sections.forEach(function(section) {
                console.log("Phần: " + section.title);
                
                section.questions.forEach(function(question) {
                    console.log("Câu hỏi: " + question.text);
                    question.answers.forEach(function(answer) {
                        console.log("Đáp án " + answer.label + ": " + answer.text);
                    });
                });
            });
        },
        error: function(error) {
            console.log("Lỗi khi lấy dữ liệu: ", error);
        }
    });
});
