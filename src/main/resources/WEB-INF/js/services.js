'use strict';

/* Services */


// Demonstrate how to register services
// In this case it is a simple value service.

angular.module('app.services', []).

service('userService',['$http', function($http) {
	var user = {};
	var professors = {};
	var update = function() {
		$http.post('/api/get-current-user').success(function(data, status) {
			user = data;
			user.role = data.role == "ROLE_STUDENT" ? "Student" :
									data.role == "ROLE_PROFESSOR" ? "Professor" :
									data.role == "ROLE_REGISTRAR" ? "Registrar" : "Graduate Program Director";
			if(user.role == "Graduate Program Director")
				$http.post('/api/user/get-all-professors').success(function(data, status) {
					professors = data;
				})
		});
	};
	update();
	return {
		getUser: function() {
			return user;
		},
		updateUser: update,
		getProfessors: function() {
			return professors;
		}
	};
}]);

// service('userService',['$http', '$q', function($http, $q) {
// 	var deferred = $q.defer();

// 	$http.post('/api/get-current-user').success(function(user, status) {
// 		deferred.resolve(user);
// 	});
	
// 	return deferred.promise;
// }]);