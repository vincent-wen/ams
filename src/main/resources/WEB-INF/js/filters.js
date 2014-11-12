'use strict';

/* Filters */

angular.module('app.filters', [])

.filter('isEmpty',function() {
	return function(object) {
		return angular.element.isEmptyObject(object);
	};
});;