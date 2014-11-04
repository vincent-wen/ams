'use strict';

/* Controllers */

angular.module('app.controllers', []).

controller('AppCtrl', ['$scope', 'userService', '$window', function($scope, userService, $window) {
	userService.then(function(user) {
		$scope.user = user;
	})

	$scope.logout = function() {
		$window.location.href = "/logout";
	}
}]).

controller('CourseCtrl', ['$scope', '$http', 'userService', function($scope, $http, userService){
	$scope.courseNameorId = '';
	$scope.courses = {};
	userService.then(function(user) {
		$scope.user = user;
	})

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
		$http.post('/api/course/search-all', {})
		.success(function(data, status) {
			$scope.courses = data;
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.registerSection = function(section) {
		$http.post('/api/course/register', section.id)
		.success(function(data, status) {
			userService.then(function(user) {
				$scope.user = user;
			})
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.showEnrolledStudents = function(section) {
		$http.post('/api/section/get-enrolled-students', section.id)
		.success(function(data, status) {
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}
}]).

controller('profileCtrl', ['$scope', 'userService', function($scope, userService){
	userService.then(function(user) {
		console.log(user);
		$scope.user = user;
		$scope.role = user.role == "ROLE_STUDENT" ? "Student" :
			user.role == "ROLE_PROFESSOR" ? "Professor" :
			user.role == "ROLE_REGISTRAR" ? "Registrar" : "Graduate Program Director";
		console.log($scope.role);
	})
	
}]);