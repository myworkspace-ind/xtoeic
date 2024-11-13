// DOM
var start_page = _('start_page');
var test_page = _('test_page');
var result_page = _('result_page');
var info_toeic = _('info_toeic');
var start_test = _('start_test');
var answer_sheet = _('answer_sheet');
var timeToeicId = _('time_remain');
var nextBtn = _("nextBtn");
var backBtn = _("backBtn");
var submitBtn = _("submitBtn");
var saveBtn = _("saveBtn");
var question_contain = _("question_contain");
var multi_question_area = _("multi_question");
var toeicForm = document.getElementsByClassName("toeic-form");
var submitted = false;
var currentQuestionNum = 1;
var savedItems = true;
var firstPartNum = 1;

// Init
var listAssessment;
var idAssessment;
var assessmentData;
var mainData;
var arrayRealQuestion;
var savedAnswers;
var introUrl;
var partsMetaData = {};
var audioNumber = new Audio();
var audioLook = new Audio();
var feedback;
var currentPart;
var isReady = false;
var playLook = false;
var played = false;
var playedIntro = false;
var playingDirection = false;
var playingIntro = false;
var playedPartDirections = [false, false, false, false, false, false, false];
var timeLimit_minute;
var indexPart = 1;
var indexQuestion = 0;
var isStart = false;
var TIME_REMAINING_SECOND = 0;
var TIME_REMAINING_QUESTION_SECOND;
var counterTimeToeic;
var arrSelectedAnswers = [];
var choseAssessment;
var loadFilePermission = true;
var itemToSaveQuery = [];
var saveQueryInterval;
var savingItem;
var errorAudioRetry = false;
var renderedSavedAnswers = false;
var faceRec;
var images = [];
var countSaveImage = 0;
var start = false;
var end = false;
var template = true; // true = question by question; false = part by part

// variables for IndexedDB
var indexedDB, dbVersion, request, db, createObjectStore, getFile, getFile2, addToDb, getFromDb, getAllFromDb, deleteFromDb, searchFromDb, deleteFromDbWithRange;
window.indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.OIndexedDB || window.msIndexedDB,
	IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.OIDBTransaction || window.msIDBTransaction;
var loadRetry = [];
var localAudioUrl;
var localImageUrl;
var localNumberUrl;
var localLookUrl;
var localDirectImg;
var localDirectAudio;

// password to decrypt.
var cryptoPassword;

var lengthPartAll = {
	part1: 0,
	part2: 0,
	part3: 0,
	part4: 0,
	part5: 0,
	part6: 0,
	part7: 0,
};

// This flag is used in begin.html of the TOEIC data.
var isDisplayedBegin = false;

// Event btn
start_test.addEventListener("click", function () {
	if (faceRec.startRecognition) {
		startCamera();
		$("#start_page").addClass("hidden");
	} else {
		handleFlag(true);
	}
});
// Event for next button
nextBtn.addEventListener("click", function () {
	nextQuestion();
});
// Event for back button
backBtn.addEventListener("click", function () {
	prevQuestion();
});

// Event for submit button
submitBtn.addEventListener("click", function () {
	submitForGrading(true);
});

// Event for save button
saveBtn.addEventListener("click", function () {
	submitForGrading(false);
});

// Event for audio, set time remain question
audioNumber.addEventListener("ended", () => {
	if (playLook) {
		audioLook.play();
	} else {
		_("audioQuestion").play();
	}

});

audioLook.addEventListener("ended", () => {
	_("audioQuestion").play();
});

audioNumber.onerror = function (e) {
	if ($(e.path[0]).attr("src")) {
		errorAlert(error_number);
	}
};

audioLook.onerror = function (e) {
	if ($(e.path[0]).attr("src")) {
		errorAlert(error_look);
	}
};

