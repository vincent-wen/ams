'use strict';

/* Controllers */

angular.module('app.controllers', []).

controller('AppCtrl', ['$scope', 'userService', '$window', function($scope, userService, $window) {
	$scope.user = userService.getUser;

	$scope.logout = function() {
		$window.location.href = "/logout";
	}

}]).

controller('CourseCtrl', ['$scope', '$http', 'userService', function($scope, $http, userService){
	$scope.courseNameorId = '';
	$scope.courses = {};
	$scope.registerError = '';
	$scope.registerSuccess = '';
	$scope.user = userService.getUser;
	$scope.professors = userService.getProfessors;
	$scope.instructor = [];

	$scope.searchById = function() {
		console.log($scope.courseNameorId);
		$http.post('/api/course/search-by-id', $scope.courseNameorId)
		.success(function(data, status) {
			$scope.courses = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchByName = function() {
		$http.post('/api/course/search-by-name', $scope.courseNameorId)
		.success(function(data, status) {
			$scope.courses = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchAll = function() {
		$http.post('/api/course/search-all')
		.success(function(data, status) {
			$scope.courses = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.registerSection = function(section) {
		$http.post('/api/student/register-course', section.id)
		.success(function(data, status) {
			$scope.registerError = '';
			$scope.registerSuccess = data;
			userService.updateUser();
		})
		.error(function(data, status) {
			$scope.registerError = data;
			$scope.registerSuccess = '';
			console.log(data);
		})
	}

	$scope.showCourseDescription = function(course) {
		$scope.currentCourse = course;
		angular.element('#course-description').modal({
			backdrop: 'static'
		}).modal('show');
	}

	$scope.showEnrolledStudents = function(section) {
		$http.post('/api/section/get-enrolled-students', section.id)
		.success(function(data, status) {
			$scope.enrolledStudents = data;
			angular.element('#enrolled-students').modal({
				backdrop: 'static'
			}).modal('show');
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.displayProfessors = function() {
		angular.element('#professors_list').dropdown();
	}

	$scope.chooseProfessor = function(professorName, key, id) {
		$scope.instructor[key][id] = professorName;
	}

	$scope.changeInstructor = function(key, id) {
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
			$scope.switchMode('instructorMode', key, id);
			console.log(data);
		})
		.error(function(data, status) {
			errorContainer.html(data);
			console.log(data);
		})
	}

	$scope.weekdays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
	$scope.timeMode = [];
	$scope.instructorMode = [];
	$scope.locationMode = [];
	
	$scope.switchMode = function(mode, key, id) {
		$scope[mode][key][id] = !$scope[mode][key][id];
	}

	$scope.changeTime = function(event, key, id) {
		event.preventDefault();
		var section = $scope.courses[key].courseSections[id];
		var parentElem = angular.element(event.currentTarget).parent();
		var errorContainer = parentElem.parent().siblings('.text-danger');
		var updatedSection = {
			timeslot: {
				startTime: parentElem.siblings('input[name="startTime"]').val(),
				endTime: parentElem.siblings('input[name="endTime"]').val(),
			},
			weekday: parentElem.siblings('select').val(),
			id: section.id
		}
		$http.post('/api/section/change-time', updatedSection)
		.success(function(data, status) {
			errorContainer.html('');
			section.weekday = updatedSection.weekday;
			section.timeslot.startTime = updatedSection.timeslot.startTime;
			section.timeslot.endTime = updatedSection.timeslot.endTime;
			$scope.switchMode('timeMode', key, id);
		})
		.error(function(data, status) {
			errorContainer.html(data);
		})
	}

	$scope.changeLocation = function(event, key, id) {
		event.preventDefault();
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
			$scope.switchMode('locationMode', key, id);
		})
		.error(function(data, status) {
			console.log(errorContainer);
			errorContainer.html(data);
		})
	}
}]).

controller('profileCtrl', ['$scope', '$http', 'userService', function($scope, $http, userService){
	$scope.user = userService.getUser;
	$scope.phoneNumber = $scope.user().phoneNumber;
	$scope.email = $scope.user().email;
	$scope.editPhoneNumberMode = false;
	$scope.editEmailMode = false;

	$scope.switchEditMode = function(mode) {
		$scope[mode] = !$scope[mode];
	}

	$scope.changePhoneNumber = function() {
		$http.post('/api/user/change-phone-number', $scope.phoneNumber)
		.success(function(data, status) {
			$scope.phoneNumberError = '';
			userService.updateUser();
			$scope.editPhoneNumberMode = false;
		})
		.error(function(data, status) {
			$scope.phoneNumberError = data;
		})
	}

	$scope.changeEmail = function() {
		$http.post('/api/user/change-email', $scope.email)
		.success(function(data, status) {
			$scope.emailError = '';
			userService.updateUser();
			$scope.editEmailMode = false;
		})
		.error(function(data, status) {
			$scope.emailError = data;
		})
	}

	$scope.displaySections = function(courseId) {
		$http.post('/api/course/search-by-id', courseId)
		.success(function(data, status) {
			$scope.sections = data[0].courseSections;
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
			userService.updateUser();
		})
		.error(function(data, status) {
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
		.error(function(data, status) {
			console.log(data);
		})
	}

}]).

controller('StudentCtrl', ['$scope', '$http', 'userService', function($scope, $http, userService) {
	$scope.user = userService.getUser;

	$scope.searchById = function() {
		console.log($scope.studentNameorId);
		$http.post('/api/student/search-by-id', $scope.studentNameorId)
		.success(function(data, status) {
			$scope.students = data;
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchByName = function() {
		$http.post('/api/student/search-by-name', $scope.studentNameorId)
		.success(function(data, status) {
			$scope.students = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchAll = function() {
		$http.post('/api/student/search-all', {})
		.success(function(data, status) {
			$scope.students = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.showStudentDetails = function(student) {
		$scope.chosenStudent = student;
		angular.element('#student-details').modal({
			backdrop: 'static'
		}).modal('show');
	}
}]);