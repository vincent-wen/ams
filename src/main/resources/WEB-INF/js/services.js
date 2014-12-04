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
				updateStudentRecord(user);
				$rootScope.$emit('student:updated', user);
			}
			if(user.role == "Professor") {
				user.enrolledStudents = {};
			}
		});
	}
	update();
	var updateStudentRecord = function(student) {
		student.records = {};
		for(var i=0; i<student.completedCourses.length; i++) {
			var course = student.completedCourses[i];
			if(student.records[course.year] == undefined) {
				student.records[course.year] = {};
				student.records[course.year][course.term] = [];
			}
			if(student.records[course.year][course.term] == undefined) {
				student.records[course.year][course.term] = [];
			}
			student.records[course.year][course.term].push(course);
		}
	}

	return {
		getUser: function() {
			return user;
		},
		updateUser: update,
		getProfessors: function() {
			return professors;
		},
		updateStudentRecord : updateStudentRecord
	};
}]).

service('gradingSystem', ['$http', '$rootScope', 'userService', 
	function($http, $rootScope, userService) {
		var grades = null;
		var sortedGrades = [];
		var GPA = {};
		var earnCredits = {};
		var user = userService.getUser;

		var update = function() {
			$http.post('/api/course/get-grading-system')
			.success(function(data, status) {
				grades = data;
				sortedGrades = sortGrades(grades);
				if(user() != null && user().role == "Student")
					calculateGPAandCredits(user());
			})
		}
		update();

		var sortGrades = function(grades) {
			var array = [];
			for(var letter in grades) {
				array.push([letter, grades[letter]]);
			}
			array.sort(function(a, b) {
				return b[1] - a[1];
			})
			console.log(array);
			return array;
		}

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
			getSortedGrades: function() {
				return sortedGrades;
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
}]).

service('constants', ['$http', function($http) {
	var year = '';
	var term = '';
	$http.get('/api/get-current-year')
	.success(function(data) {
		year = data;
	})
	$http.get('/api/get-current-term')
	.success(function(data) {
		term = data;
	})
	return {
		year: function() {
			return year;
		},
		term: function() {
			return term;
		}
	}
}]);

