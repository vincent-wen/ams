'use strict';

/* Filters */

angular.module('app.filters', []).

filter('isEmpty', function() {
	return function(object) {
		return angular.element.isEmptyObject(object);
	}
}).

filter('formatError', function() {
	var err = "Error: ";
	return function(error) {
		if(error != undefined && typeof error === 'string' && error.slice(0, err.length) == err) {
			return error.slice(err.length);
		}
		return 'Your operation is not successful. Please try again or contact vincent.wen77@gmail.com.';
	}
}).

filter('formatTime', function() {
	return function(time) {
		if(time == undefined || time == null) return time;
		return time.hour+':'+time.minute;
	};
});;