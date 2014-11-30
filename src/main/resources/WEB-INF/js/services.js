'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('app.services', []).

service('userService', ['$http', '$rootScope', function($http, $rootScope) {
	
	var user = null;
	var professors = {};
	var update = function() {
		$http.post('/api/get-current-user').success(function(data, status) {
			user = data;
			user.role = data.role == "ROLE_STUDENT" ? "Student" :
			data.role == "ROLE_PROFESSOR" ? "Professor" :
			data.role == "ROLE_REGISTRAR" ? "Registrar" : "Graduate Program Director";
			if(user.role == "Graduate Program Director")
				$http.post('/api/user/get-all-professors')
			.success(function(data, status) {
				professors = data;
			})
			if(user.role == "Student") {
				$rootScope.$emit('student:updated', user);
			}
		});
	}
	// var updateCompletedCourses = function(student) {
	// 	$http.post('/api/student/get-completed-courses', student.id)
	// 	.success(function(data, status) {
	// 		if(student == undefined) {
	// 			user.completedCourses= data;
	// 		} else {
	// 			student.completedCourses = data;
	// 		}
	// 		$rootScope.$emit('student:completedCourses:updated', student);
	// 	})	
	// }
	update();

	return {
		getUser: function() {
			return user;
		},
		updateUser: update,
		getProfessors: function() {
			return professors;
		}
	};
}]).

service('gradingSystem', ['$http', '$rootScope', 'userService', 
	function($http, $rootScope, userService) {
		var grades = null;
		var GPA = {};
		var earnCredits = {};
		var user = userService.getUser;

		var update = function() {
			$http.post('/api/course/get-grading-system')
			.success(function(data, status) {
				grades = data;
				if(user() != null && user().role == "Student")
					calculateGPAandCredits(user());
			})
		}
		update();

		var calculateGPAandCredits = function(student) {
			var sumCredits = 0;
			var sumGrades = 0;

			for(var i=0; i<student.completedCourses.length; i++) {
				var course = student.completedCourses[i];
				sumCredits += parseFloat(course.credits);
				sumGrades += parseFloat(grades[course.grade]) * parseFloat(course.credits);
			}

			student.GPA = sumGrades / sumCredits;
			student.earnCredits = sumCredits;
		}

		$rootScope.$on('student:updated', function(event, student) {
			if(student.role != "Student" || grades == null) return;
			calculateGPAandCredits(student);
		})

		return {
			getGrades: function() {
				return grades;
			},
			update: function(student) {
				return calculateGPAandCredits(student);
			}
		}
	}]).

service('paymentResult', function() {
	var result = {};
	return {
		get: function(name) {return result[name];},
		set: function(name, value) {
			result[name] = value;
			setTimeout(function() {
				result[name] = '';
				console.log(result[name]);
			}, 5000);
		}
	}
}).

service('inquiryService', ['$http', function($http) {
	var inquires = [];
	var update = function() {
		$http.post('/api/inquiry/all').success(function(data) {
			inquires = data;
		})
	}
	update();
	// update every minute
	setInterval(function(){
		update();
	}, 60000);
	return {
		get: function() {return inquires;}
	}
}]);

