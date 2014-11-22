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
		if(error != undefined && error.slice(0, err.length) == err) {
			return error.slice(err.length);
		}
		return '';
	}
});