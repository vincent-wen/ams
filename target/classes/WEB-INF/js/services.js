'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('app.services', []).

service('userService',['$http', '$q', function($http, $q) {
	var deferred = $q.defer();

	$http.post('/api/get-current-user').success(function(user, status) {
		deferred.resolve(user);
	});
	
	return deferred.promise;
}]);