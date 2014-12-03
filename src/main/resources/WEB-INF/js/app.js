'use strict';

// Declare app level module which depends on filters, and services

angular.module('app', [
  'app.controllers',
  'app.filters',
  'app.services',
  'app.directives',
  'ngRoute',
  'ngAnimate',
  'angularUtils.directives.dirPagination'
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
  when('/inquiry', {
    templateUrl: '/api/partials/information-inquiry'
  }).
  when('/inquiries', {
    templateUrl: '/api/partials/display-inquiries'
  }).
  when('/grade-submit', {
    templateUrl: '/api/partials/grade-submit'
  }).
  when('/payment/paypal/:payment_result', {
    templateUrl: '/api/partials/tuition-payment',
    controller: 'paymentCtrl'
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
