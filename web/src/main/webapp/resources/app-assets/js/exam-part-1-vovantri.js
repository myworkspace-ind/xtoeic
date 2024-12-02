// Biến toàn cục
let currentIndex = 0;


function selectAnswer(answerId) {
    const answerForms = document.querySelectorAll('.answer-form');
    
    answerForms.forEach((form, index) => {
        const radios = form.querySelectorAll('input[type="radio"]');
        
        radios.forEach(radio => {
            if (radio.getAttribute('answerid') == answerId) {
                radio.checked = true;
                
                // Cập nhật các giá trị cho text box answerId và answerText
                const questionNumber = index + 1; // Xác định câu hỏi hiện tại (1, 2, 3, ...)
                const answerText = String.fromCharCode(65 + Array.from(radios).indexOf(radio)); // Tính A, B, C, ...

                // Cập nhật giá trị cho text box answerId và answerText
                const answerIdElement = document.getElementById('answerId-' + questionNumber);  // Sử dụng id 'answerId-X'
                const answerTextElement = document.getElementById('answerText-' + questionNumber);  // Sử dụng id 'answerText-X'

                // Kiểm tra nếu các phần tử tồn tại và cập nhật giá trị
                if (answerIdElement) {
                    answerIdElement.value = answerId;
                }
                if (answerTextElement) {
                    answerTextElement.value = questionNumber + '-' + answerText;
                }
            }
        });
    });
}

 
 

// Hàm chuyển đến câu hỏi tiếp theo, và tự phát audio câu kế
function nextQuestion() {
    const allQuestions = document.querySelectorAll('.test-box');  // Lấy tất cả câu hỏi
    const allAudioElements = document.querySelectorAll('audio');  // Lấy tất cả audio elements
    const allAnswerForms = document.querySelectorAll('.answer-form'); // Lấy tất cả các div chứa câu trả lời
    
    // Dừng tất cả audio đang phát
    allAudioElements.forEach(audio => {
        audio.pause();
        audio.currentTime = 0;   
    });

    // Kiểm tra nếu chưa đến câu hỏi cuối cùng
    if (currentIndex < allQuestions.length - 1) {
        // Ẩn câu hỏi hiện tại và hiển thị câu hỏi tiếp theo
        allQuestions[currentIndex].classList.remove('active');
        allQuestions[currentIndex].classList.add('hidden');
        
        // Xóa class selected-border mark-color khỏi câu trả lời của câu hiện tại
        allAnswerForms[currentIndex].classList.remove('selected-border', 'mark-color');
        
        currentIndex++;
        
        // Hiển thị câu hỏi tiếp theo
        allQuestions[currentIndex].classList.remove('hidden');
        allQuestions[currentIndex].classList.add('active');
        
        // Thêm class selected-border mark-color cho câu trả lời của câu tiếp theo
        allAnswerForms[currentIndex].classList.add('selected-border', 'mark-color');

        // Lấy phần tử audio của câu hỏi tiếp theo và phát
        const audioElement = document.getElementById('audio-' + currentIndex);
        if (audioElement) {
            audioElement.play();  // Phát audio của câu hỏi tiếp theo
        }
    }
}



// Hàm này sẽ được gọi khi audio kết thúc
function onAudioEnded(index) {
    // Sau khi audio kết thúc, tự động chuyển sang câu tiếp theo
    nextQuestion();
}

// Khi bấm nút start thì nó tự add class 'selected-border', 'mark-color' và tự phát audio câu đầu tiên
window.onload = function() {
    const allAnswerForms = document.querySelectorAll('.answer-form'); 
    
    // Thêm class cho câu đầu tiên
    if (allAnswerForms.length > 0) {
        allAnswerForms[0].classList.add('selected-border', 'mark-color');
    } 
    
    const audioElement = document.getElementById('audio-' + currentIndex);
    if (audioElement) {
        audioElement.play();  
    }
	
	const allAudioElements = document.querySelectorAll('audio');

	    allAudioElements.forEach((audio, index) => {
	        audio.onended = function() {
	            onAudioEnded(index); // Gọi hàm khi audio kết thúc
	        };
	    });
};

// Đồng hồ đếm ngược
let totalTimeInSeconds = 120 * 60; // 120 phút

// Hàm cập nhật đồng hồ
function updateClock() {
    const timeRemainElement = document.getElementById("time_remain");

    // Tính phút và giây còn lại
    const minutes = Math.floor(totalTimeInSeconds / 60);
    const seconds = totalTimeInSeconds % 60;

    // Hiển thị thời gian theo định dạng MM:SS
    timeRemainElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

    // Giảm thời gian còn lại
    if (totalTimeInSeconds > 0) {
        totalTimeInSeconds--;
    } else {
        // Khi hết thời gian
        clearInterval(timerInterval); // Dừng đồng hồ
        alert("Time's up!"); // Thông báo
    }
}

// Cập nhật đồng hồ mỗi giây
const timerInterval = setInterval(updateClock, 1000);

// Gọi ngay lần đầu để tránh trễ 1 giây
updateClock();


function submitForm(index) {
	        const form = document.getElementById(`form-${index}`);
	        const formData = new FormData(form);
	
	        fetch('/toeic-web/saveQuestionOfPart1', {
	            method: 'POST',
	            body: formData
	        })
	        .then(response => response.text())
	        .then(data => {
	            if (data === 'success') {
	                alert('Câu trả lời đã được lưu thành công!');
	            } else {
	                alert('Đã xảy ra lỗi khi lưu câu trả lời.');
	            }
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            alert('Đã xảy ra lỗi khi gửi yêu cầu.');
	        });
	    }
