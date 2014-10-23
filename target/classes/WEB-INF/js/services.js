'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('app.services', []).

service('userService',['$http', '$q', function($http, $q) {
	var user = {};
	var deferred = $q.defer();

	$http.post('/api/get-current-user').success(function(data, status) {
		user = data;
		deferred.resolve({
			username: user.username,
			role: user.role
		});
	});
	return deferred.promise;
}]);