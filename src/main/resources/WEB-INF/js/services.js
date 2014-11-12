'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('app.services', []).

service('userService', ['$http', 'gradingSystem', function($http, gradingSystem) {
	var user = {};
	var professors = {};
	var update = function() {
		$http.post('/api/get-current-user').success(function(data, status) {
			user = data;
			user.role = data.role == "ROLE_STUDENT" ? "Student" :
									data.role == "ROLE_PROFESSOR" ? "Professor" :
									data.role == "ROLE_REGISTRAR" ? "Registrar" : "Graduate Program Director";
			if(user.role == "Graduate Program Director")
				$http.post('/api/user/get-all-professors').success(function(data, status) {
					professors = data;
				})
			if(user.role == "Student") {
				$http.post('/api/student/get-completed-courses')
				.success(function(data, status) {
					user.completedCourses = data;
					console.log(user);
				})
			}
		});
	};
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

service('gradingSystem', ['$http', function($http) {
	var grades = {};
	var update = function() {
		$http.post('/api/course/get-grading-system').success(function(data, status) {
			grades = data;
		})
	}
	update();
	return {
		getGrades: function() {
			return grades;
		},
		updateGrades: update
	}
}]);

// service('userService',['$http', '$q', function($http, $q) {
// 	var deferred = $q.defer();

// 	$http.post('/api/get-current-user').success(function(user, status) {
// 		deferred.resolve(user);
// 	});
	
// 	return deferred.promise;
// }]);