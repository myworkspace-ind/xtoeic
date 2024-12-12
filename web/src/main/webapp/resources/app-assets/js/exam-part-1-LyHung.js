
		// Hàm hiển thị văn bản của feedback
		function showAudioTextDialog() {
			// Tìm phần tử feedback tương ứng với câu hỏi hiện tại
			const currentFeedback = document
					.querySelector('.test-box.active .toeic-select [id^="feedback-"]');

			if (currentFeedback) {
				const feedbackText = currentFeedback
						.getAttribute('data-feedback'); // Lấy nội dung feedback

				// Hiển thị nội dung feedback trong hộp thoại
				document.getElementById('audioTextContent').innerText = feedbackText
						|| "Không có feedback cho câu hỏi này.";
				document.getElementById('audioTextDialog').style.display = 'block';
			} else {
				alert("Không tìm thấy feedback cho câu hỏi hiện tại.");
			}
		}

		// Hàm đóng hộp thoại
		function closeAudioTextDialog() {
			document.getElementById('audioTextDialog').style.display = 'none';
		}

		// Hàm đóng hộp thoại
		function closeAudioTextDialog() {
			document.getElementById('audioTextDialog').style.display = 'none';
		}

		// Hàm trả về text của từng file âm thanh (có thể thay bằng API)
		function getAudioText(audioUrl) {
			// Ví dụ dữ liệu giả lập (thay bằng dữ liệu thực tế)
			const audioTextMap = {
				"audio1.mp3" : "This is the text for audio1.",
				"audio2.mp3" : "This is the text for audio2.",
			// Bạn có thể thêm nhiều file và nội dung tương ứng ở đây
			};

			const audioFileName = audioUrl.split('/').pop(); // Lấy tên file từ URL
			return audioTextMap[audioFileName]
					|| "Không có văn bản tương ứng với file âm thanh này.";
		}

		
		