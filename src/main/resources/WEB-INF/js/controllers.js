'use strict';

/* Controllers */

angular.module('app.controllers', []).

controller('AppCtrl', ['$scope', 'userService', '$window', '$rootScope',
	function($scope, userService, $window, $rootScope) {
		$scope.user = userService.getUser;

		$scope.logout = function() {
			$window.location.href = "/logout";
		}

		$scope.showContent = function() {
			angular.element('#main-content').css("opacity", 1);
		}

		$rootScope.$on('loading-begin', function() {
			angular.element('#loading').css('display', 'block');
		});

		$rootScope.$on('loading-complete', function() {
			angular.element('#loading').css('display', 'none');
		});
	}]).

controller('CourseCtrl', ['$scope', '$http', 'userService', 'gradingSystem', 'formatErrorFilter',
	function($scope, $http, userService, gradingSystem, formatErrorFilter){
		$scope.weekdayPreference = {
			Monday: true,
			Tuesday: true,
			Wednesday: true,
			Thursday: true,
			Friday: true,
		}
		$scope.schedules = [];
		$scope.schedulePreference = {};
		$scope.weekdays = Object.keys($scope.weekdayPreference);

		$scope.courseNameorId = '';
		$scope.studentNameorId = '';
		$scope.courses = {};
		$scope.students = {};
		$scope.registerError = '';
		$scope.registerSuccess = '';
		$scope.dropSuccess = '';

		$scope.user = userService.getUser;
		$scope.professors = userService.getProfessors;
		$scope.instructor = [];
		$scope.chosen = {};

		$scope.searchError = '';
		$scope.registerError = '';
		$scope.registerSuccess = '';

		$scope.timeMode = [];
		$scope.instructorMode = [];
		$scope.locationMode = [];
		$scope.capacityMode = [];

		$scope.searchById = function() {
			$scope.searchError = '';
			$scope.registerError = '';
			$scope.registerSuccess = '';
			$http.post('/api/course/search-by-id', $scope.courseNameorId)
			.success(function(data, status) {
				$scope.courses = data;
				if($scope.courses.length == 0) {
					$scope.searchError = "No corresponding course is found. Please try again.";
				} 
			});
		}

		$scope.searchByName = function() {
			$scope.searchError = '';
			$scope.registerError = '';
			$scope.registerSuccess = '';
			$http.post('/api/course/search-by-name', $scope.courseNameorId)
			.success(function(data, status) {
				$scope.courses = data;
				if($scope.courses.length == 0) {
					$scope.searchError = "No corresponding course is found. Please try again.";
				} 
			})
		}

		$scope.searchAll = function() {
			$scope.searchError = '';
			$scope.registerError = '';
			$scope.registerSuccess = '';
			$http.post('/api/course/search-all')
			.success(function(data, status) {
				$scope.courses = data;
				updateSchedules();
				if($scope.courses.length == 0) {
					$scope.searchError = "No corresponding course is found. Please try again.";
				} 
			})
		}

		var updateSchedules = function() {
			$scope.schedulePreference = {};
			for(var i=0; i<$scope.courses.length; i++) {
				for(var j=0; j<$scope.courses[i].courseSections.length; j++) {
					var schedule = $scope.courses[i].courseSections[j].schedule;
					var timeslot = schedule.startTime.hour+':'+schedule.startTime.minute+' - '+
													schedule.endTime.hour+':'+schedule.endTime.minute;
					$scope.schedulePreference[timeslot] = true;
				}
			}
		}

		$scope.sectionFilter = function(section) {
			var timeslot = section.schedule.startTime.hour+':'+section.schedule.startTime.minute+' - '+
											section.schedule.endTime.hour+':'+section.schedule.endTime.minute;
			return $scope.weekdayPreference[section.schedule.weekday] && 
			$scope.schedulePreference[timeslot];
		}

		$scope.registerSectionRequest = function(section, courseId) {
			$scope.chosen.section = section;
			$scope.chosen.courseId = courseId;
			angular.element('#confirm-register-course').modal({
				backdrop: 'static'
			}).modal('show');
		}

		$scope.registerSection = function(sectionId) {
			$http.post('/api/student/register-course', sectionId)
			.success(function(data, status) {
				$scope.registerError = '';
				$scope.registerSuccess = data;
				userService.updateUser();
				$scope.chosen.section.size ++;
				angular.element('#confirm-register-course').modal('hide');
			})
			.error(function(data, status) {
				$scope.registerError = data;
				$scope.registerSuccess = '';
				angular.element('#confirm-register-course').modal('hide');
			})
		}

		$scope.searchStudents = function(value) {
			var url;
			if(value == parseInt(value)) {
				url = '/api/student/search-by-id'
			} else {
				url = '/api/student/search-by-name'
			}
			$http.post(url, value).success(function(data, status) {
				$scope.students = data;
			})
		}

		$scope.registerSectionForStudent = function(sectionId) {
			$scope.chosen.sectionId = sectionId;
			angular.element('#register-for-student').modal({
				backdrop: 'static'
			}).modal('show');
		}

		$scope.confirmRegisterForStudent = function() {
			$http.post('/api/student/register-course/' + $scope.chosen.studentId, $scope.chosen.sectionId)
			.success(function(data, status) {
				$scope.registerError = '';
				$scope.registerSuccess = data;
			})
			.error(function(data, status) {
				$scope.registerError = data;
				$scope.registerSuccess = '';
			})
		}

		$scope.dropSectionForStudent = function(studentId) {
			$scope.dropSuccess = '';
			$scope.chosen.studentId = studentId;
			angular.element('#drop-for-student-confirmation').modal({
				backdrop: 'static'
			}).modal('show');
		}

		$scope.confirmDropSectionForStudent = function() {
			$http.post('/api/student/drop-course/' + $scope.chosen.studentId, $scope.chosen.sectionId)
			.success(function(data, status) {
				$scope.dropSuccess = data;
				angular.element('#drop-for-student-confirmation').modal('hide');
				for(var i=0; i<$scope.enrolledStudents.length; i++) {
					if($scope.enrolledStudents[i].id == $scope.chosen.studentId) {
						$scope.enrolledStudents.splice(i,1);
					}
				}
			})
		}

		$scope.showCourseDescription = function(course) {
			$scope.currentCourse = course;
			angular.element('#course-description').modal({
				backdrop: 'static'
			}).modal('show');
		}

		$scope.showEnrolledStudents = function(sectionId) {
			$scope.dropSuccess = '';
			$scope.chosen.sectionId = sectionId;
			$http.post('/api/section/get-enrolled-students', sectionId)
			.success(function(data, status) {
				$scope.enrolledStudents = data;
				angular.element('#enrolled-students').modal({
					backdrop: 'static'
				}).modal('show');
			})
		}

		$scope.switchMode = function(mode, key, id, event) {
			$scope[mode][key][id] = !$scope[mode][key][id];
			if(!$scope[mode][key][id]) {
				var parent = angular.element(event.currentTarget).parent();
				parent.children('').removeClass('hidden');
				parent.children('#confirm-button').addClass('hidden');
			}
		}

		$scope.confirmRequest = function(event) {
			var saveButton = angular.element(event.currentTarget);
			var confirmButton = saveButton.siblings('#confirm-button');
			saveButton.addClass('hidden');
			confirmButton.removeClass('hidden');
		}

		$scope.changeTime = function(event, key, id) {
			event.preventDefault();

			var section = $scope.courses[key].courseSections[id];
			var parentElem = angular.element(event.currentTarget).parent();
			var errorContainer = parentElem.parent().siblings('.text-danger');
			var schedule = {
				startTime: parentElem.siblings('input[name="startTime"]').val(),
				endTime: parentElem.siblings('input[name="endTime"]').val(),
				weekday: parentElem.siblings('select').val(),
				sectionObjectId: section.id
			}
			$http.post('/api/section/change-time', schedule)
			.success(function(data, status) {
				errorContainer.html('');
				section.schedule.weekday = data.weekday;
				section.schedule.startTime = data.startTime;
				section.schedule.endTime = data.endTime;
				updateSchedules();
				$scope.switchMode('timeMode', key, id, event);
			})
			.error(function(data, status) {
				errorContainer.html(formatErrorFilter(data));
			})
		}

		$scope.changeLocation = function(event, key, id) {
			event.preventDefault();
			var confirmButton = angular.element(event.currentTarget);
			confirmButton.addClass('hidden');
			confirmButton.siblings().removeClass('hidden');

			var section = $scope.courses[key].courseSections[id];
			var location = angular.element(event.currentTarget).parent().siblings('input[name="location"]');
			var errorContainer = location.parent().siblings('.text-danger');
			var updatedSection = {
				id: section.id,
				location: location.val()
			}
			$http.post('/api/section/change-location', updatedSection)
			.success(function(data, status) {
				errorContainer.html('');
				section.location = location.val();
				$scope.switchMode('locationMode', key, id, event);
			})
			.error(function(data, status) {
				errorContainer.html(formatErrorFilter(data));
			})
		}

		$scope.changeCapacity = function(event, key, id) {
			event.preventDefault();
			var confirmButton = angular.element(event.currentTarget);
			confirmButton.addClass('hidden');
			confirmButton.siblings().removeClass('hidden');

			var section = $scope.courses[key].courseSections[id];
			var capacity = angular.element(event.currentTarget).parent().siblings('input[name="capacity"]');
			var errorContainer = capacity.parent().siblings('.text-danger');

			if(!capacity.val().match('^[1-9]+\d*$')) {
				errorContainer.html('Capacity must be an integer at least 1.');
				return;
			}
			var updatedSection = {
				id: section.id,
				capacity: capacity.val()
			}
			$http.post('/api/section/change-capacity', updatedSection)
			.success(function(data, status) {
				errorContainer.html('');
				section.capacity = capacity.val();
				$scope.switchMode('capacityMode', key, id, event);
			})
			.error(function(data, status) {
				errorContainer.html(formatErrorFilter(data));
			})
		}

		$scope.displayProfessors = function() {
			angular.element('#professors_list').dropdown();
		}

		$scope.chooseProfessor = function(professorName, key, id) {
			$scope.instructor[key][id] = professorName;
		}

		$scope.changeInstructor = function(event, key, id) {
			event.preventDefault();
			var confirmButton = angular.element(event.currentTarget);
			confirmButton.addClass('hidden');
			confirmButton.siblings().removeClass('hidden');
			
			var section = $scope.courses[key].courseSections[id];
			var updatedSection = {
				id: section.id,
				instructor: {
					name: $scope.instructor[key][id]
				}
			}
			var errorContainer = angular.element(event.currentTarget).parent().parent().siblings('.text-danger');
			$http.post('/api/section/change-instructor', updatedSection)
			.success(function(data, status) {
				errorContainer.html('');
				section.instructor.name = $scope.instructor[key][id];
				$scope.switchMode('instructorMode', key, id, event);
			})
			.error(function(data, status) {
				errorContainer.html(formatErrorFilter(data));
			})
		}

		$scope.showStudentDetails = function(student) {
			// if($scope.user().role == 'Professor') {
			// 	$scope.chosen.student = student;
			// 	angular.element('#student-details').modal({
			// 		backdrop: 'static'
			// 	}).modal('show');
			// 	return;
			// }
			$http.post('/api/student/get-detailed-student', student.id)
			.success(function(data, status) {
				$scope.chosen.student = data;
				gradingSystem.update($scope.chosen.student);
				userService.updateStudentRecord($scope.chosen.student);
				angular.element('#student-details').modal({
					backdrop: 'static'
				}).modal('show');
			})
		}
	}]).

controller('profileCtrl', ['$scope', '$http', 'userService', 'gradingSystem', 
	function($scope, $http, userService, gradingSystem){
		$scope.user = userService.getUser;

		$scope.grades = gradingSystem.getGrades;

		$scope.phoneNumber = '';
		$scope.email = '';

		$scope.editPhoneNumberMode = false;
		$scope.editEmailMode = false;
		$scope.showChangeButton = false;

		$scope.changeSectionError = '';
		$scope.phoneNumberError = '';
		$scope.emailError = '';

		$scope.termOrder = ['FALL', 'WINTER', 'SUMMER'];

		$scope.$watch('user()', function(user) {
			if(user == null) return;
			$scope.phoneNumber = user.phoneNumber;
			$scope.email = user.email;
		})

		$scope.switchEditMode = function(mode) {
			$scope[mode] = !$scope[mode];
		}

		$scope.changePhoneNumber = function() {
			$http.post('/api/user/change-phone-number', $scope.phoneNumber)
			.success(function(data, status) {
				$scope.phoneNumberError = '';
				$scope.user().phoneNumber = $scope.phoneNumber;
				$scope.editPhoneNumberMode = false;
				$scope.phoneNumberError = '';
			})
			.error(function(data, status) {
				$scope.phoneNumberError = data;
			})
		}

		$scope.changeEmail = function() {
			$http.post('/api/user/change-email', $scope.email)
			.success(function(data, status) {
				$scope.emailError = '';
				$scope.user().email = $scope.email;
				$scope.editEmailMode = false;
			})
			.error(function(data, status) {
				$scope.emailError = data;
			})
		}

		$scope.displaySections = function(courseId, sectionId) {
			$http.post('/api/course/search-by-id', courseId)
			.success(function(data, status) {
				$scope.sections = data[0].courseSections;
				$scope.registerSectionId = sectionId;
				angular.element('#display-sections').modal({
					backdrop: 'static'
				}).modal('show');
			})
		}

		$scope.chooseSection = function(event, section) {
			event.preventDefault();
			if($scope.chosenItem != undefined)
				$scope.chosenItem.removeClass('list-group-item-info')
			$scope.chosenItem = angular.element(event.currentTarget);
			$scope.chosenItem.addClass('list-group-item-info');
			$scope.chosenSection = section;
		}

		$scope.changeSection = function() {
			if($scope.user().role != 'Student') return;
			$http.post('/api/student/change-section', $scope.chosenSection.id)
			.success(function(data, status) {
				angular.element('#display-sections').modal('hide');
				$scope.showChangeButton = false;
				userService.updateUser();
				$scope.changeSectionError = '';
			})
			.error(function(data, status) {
				$scope.showChangeButton = false;
				$scope.changeSectionError = data;
			})
		}

		$scope.dropCourseRequest = function(section) {
			$scope.chosenSection = section;
			angular.element('#confirm-drop-course').modal({
				backdrop: 'static'
			}).modal('show');
		}

		$scope.dropCourse = function(sectionId) {
			if($scope.user().role != 'Student') return;
			$http.post('/api/student/drop-course', sectionId)
			.success(function(data, status) {
				userService.updateUser();
				angular.element('#confirm-drop-course').modal('hide');
			})
		}
	}]).

controller('StudentCtrl', ['$scope', '$http', 'userService', 'gradingSystem', 
	function($scope, $http, userService, gradingSystem) {
		$scope.user = userService.getUser;
		$scope.chosenStudent = {};
		$scope.grades = gradingSystem.getGrades;
		$scope.errorMessage = '';
		$scope.successMessage = '';
		$scope.students = [];

		$scope.searchById = function() {
			$http.post('/api/student/search-by-id', $scope.studentNameorId)
			.success(function(data, status) {
				$scope.students = data;
				$scope.errorMessage = '';
			})
			.error(function(data) {
				$scope.successMessage = '';
				$scope.errorMessage = data;
			});
		}

		$scope.searchByName = function() {
			$http.post('/api/student/search-by-name', $scope.studentNameorId)
			.success(function(data, status) {
				$scope.students = data;
				$scope.errorMessage = '';
			})
			.error(function(data) {
				$scope.successMessage = '';
				$scope.errorMessage = data;
			});
		}

		$scope.searchAll = function() {
			$http.post('/api/student/search-all', {})
			.success(function(data, status) {
				$scope.students = data;
				$scope.errorMessage = '';
			})
			.error(function(data) {
				$scope.successMessage = '';
				$scope.errorMessage = data;
			});
		}

		$scope.showStudentDetails = function(student) {
			// if($scope.user().role == 'Professor') {
			// 	$scope.chosenStudent = student;
			// 	angular.element('#student-details').modal({
			// 		backdrop: 'static'
			// 	}).modal('show');
			// 	return;
			// }
			$http.post('/api/student/get-detailed-student', student.id)
			.success(function(data, status) {
				$scope.chosenStudent = data;
				userService.updateStudentRecord($scope.chosenStudent);
				gradingSystem.update($scope.chosenStudent);
				angular.element('#student-details').modal({
					backdrop: 'static'
				}).modal('show');
			})
		}

		
	}]).

controller('paymentCtrl', ['$scope', '$routeParams', '$location', 'paymentResult',
	function($scope, $routeParams, $location, paymentResult){
		$scope.result = paymentResult;
		switch($routeParams.payment_result) {
			case 'user_invalid': $scope.result.set('user_invalid', 'Current user is not student. But Payment is successful. Please contact to Registrar.'); break;
			case 'payment_failure': $scope.result.set('payment_failure', 'Payment failed. Please try again.'); break;
			case 'payment_success': $scope.result.set('payment_success', 'Payment is successful!'); break;
			case 'payment_cancel': $scope.result.set('payment_cancel', 'Payment is cancelled by user.'); break;
			default: break;
		}
		$location.url('/payment');
	}]).

controller('inquiryCtrl', ['$scope', 'inquiryService', function($scope, inquiryService){
	$scope.inquiries = inquiryService.get;
	$scope.chosenInquiry = {};

	$scope.inquiryDetails = function(inquiry) {
		$scope.chosenInquiry = inquiry;
		angular.element('#inquiry-details').modal({
			backdrop: 'static'
		}).modal('show');
	}
}]).

controller('gradeCtrl', ['$scope', '$http', 'userService', 'gradingSystem', 'constants',
	function($scope, $http, userService, gradingSystem, constants) {

		$scope.grades = gradingSystem.getGrades;
		$scope.grade = {};
		$scope.user = userService.getUser;
		$scope.errorMessage = '';
		$scope.successMessage = '';
		$scope.selected = {};
		$scope.enrolledStudents = '';
		$scope.confirmSubmit = false;

		$scope.showEnrolledStudents = function(sectionObjectId) {
			$http.post('/api/student/get-enrolled-students', sectionObjectId)
			.success(function(data, stats) {
				$scope.successMessage = '';
				$scope.enrolledStudents = data;
			})
		}

		$scope.getGrade = function(student) {
			// student will have no or just this completed course.
			$scope.grade[student.id] = '';
			for(var i=0; i<student.completedCourses.length; i++) {
				if(student.completedCourses[i].id == $scope.selected.section.courseObjectId) {
					$scope.grade[student.id] = student.completedCourses[i].grade;
				}
			}
		}

		$scope.submitGrades = function() {
			var students = [];
			for(var studentObjectId in $scope.grade) {
				students.push({
					id: studentObjectId,
					completedCourses: [{
						id: $scope.selected.section.courseObjectId,
						grade: $scope.grade[studentObjectId],
						year: constants.year,
						term: constants.term
					}],
				})
			}
			$http.post('/api/student/submit-grades', students)
			.success(function(data, status) {
				$scope.confirmSubmit = false;
				$scope.successMessage = data;
				$scope.showEnrolledStudents;
			})
		}
	}]);