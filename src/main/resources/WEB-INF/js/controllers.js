'use strict';

/* Controllers */

angular.module('app.controllers', []).

controller('AppCtrl', ['$scope', 'userService', '$window', function($scope, userService, $window) {
	userService.then(function(user) {
		$scope.user = user;
	});

	$scope.logout = function() {
		$window.location.href = "/logout";
	}
}]).

controller('CourseCtrl', ['$scope', '$http', function($scope, $http){
	$scope.courseNameorId = '';
	$scope.courses = {};

	$scope.searchById = function() {
		console.log($scope.courseNameorId);
		$http.post('/api/course/search-by-id', $scope.courseNameorId)
		.success(function(data, status) {
			$scope.courses = data;
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchByName = function() {
		$http.post('/api/course/search-by-name', $scope.courseNameorId)
		.success(function(data, status) {
			$scope.courses = data;
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}

	$scope.searchAll = function() {
		$http.post('/api/course/search-all', {})
		.success(function(data, status) {
			$scope.courses = data;
			console.log(data);
		})
		.error(function(data, status) {
			console.log(data);
		})
	}
}]);