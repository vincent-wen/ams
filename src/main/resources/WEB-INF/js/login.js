angular.module('login', ['ngAnimate'])

// set default configs for ajax request
.config(['$httpProvider', function(httpProvider) {
  var token = angular.element("meta[name='_csrf']").attr("content");
  var header = angular.element("meta[name='_csrf_header']").attr("content");

  httpProvider.defaults.headers.post[header] = token;
}])

.controller('loginCtrl', ['$scope', '$http', '$window', '$location', 
	function($scope, $http, $window, $location) {
	$scope.user = {
		username: '',
		password: ''
	};
	$scope.errorMessage = '';
	$scope.animate = "ng-show";

	$scope.login = function(event, object){
		event.preventDefault();
		var form = object.lg_form;
		setDirty(form.username);
		setDirty(form.password);
		if(form.$valid) {
			$http.post('/login',{
				username: form.username.$viewValue,
				password: form.password.$viewValue
			})
			.success(function(data, status) {
				$scope.errorMessage = '';
				$window.location.href = '/';
			})
			.error(function(data, status) {
				$scope.errorMessage = data;
			});
		}
	};

	//set dirty to form field level
  var setDirty = function(field) {
    field.$dirty = true;
    field.$pristine = false;
  };
}]).

filter('formatError', function() {
	var err = "Error: ";
	return function(error) {
		if(error.slice(0, err.length) == err) {
			return error.slice(err.length);
		}
		return '';
	}
});
