'use strict';

// Declare app level module which depends on filters, and services

angular.module('app', [
  'app.controllers',
  'app.filters',
  'app.services',
  'app.directives',
  'ngRoute',
  'ngAnimate'
]).

config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
  $routeProvider.
  when('/course', {
    templateUrl: '/api/partials/course-management'
  }).
  when('/student', {
    templateUrl: '/api/partials/student-management'
  }).
  when('/profile', {
    templateUrl: '/api/partials/my-profile'
  }).
  when('/payment', {
    templateUrl: '/api/partials/tuition-payment'
  }).
  otherwise({
    redirectTo: '/course'
  });

  $locationProvider.html5Mode(true);
}]).

// set default configs for ajax request
config(['$httpProvider', function($httpProvider) {
  var token = angular.element("meta[name='_csrf']").attr("content");
  var header = angular.element("meta[name='_csrf_header']").attr("content");

  $httpProvider.defaults.headers.post[header] = token;
}]);
