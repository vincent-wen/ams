'use strict';

/* Directives */

angular.module('app.directives', []).

directive('navigationBar', function() {
	return {
		restrict: 'E',
		templateUrl: '/api/partials/navbar'
	}
}).

directive('changePassword', ['$http', function($http) {
	return {
		restrict: 'E',
		scope: {},
		templateUrl: '/api/partials/change-password',
		link: function(scope, element, attrs) {
			var form = scope.cpw_form;

			scope.changePassword = function(event) {
				event.preventDefault();
				setDirty(form.newpassword);
				setDirty(form.oldpassword);
				setDirty(form.repeatpassword);
				if(form.$valid) {
					$http.post('/api/change-password', {
						newpassword: form.newpassword.$viewValue,
						password: form.oldpassword.$viewValue
					}).success(function(data, status) {
						scope.successMessage = data;
						scope.errorMessage = '';
						setTimeout(function() {
							angular.element('#change_password').modal('hide');
						}, 1000);
					}).error(function(data, status) {
						scope.successMessage = '';
						scope.errorMessage = data;
					});	
				}	
			}

			angular.element('#change_password').on('show.bs.modal hide.bs.modal', function (e) {
				scope.successMessage = '';
				scope.errorMessage = '';
				scope.newpassword = '';
				scope.oldpassword = '';
				scope.repeatpassword = '';
				form.$setPristine();
			})

			//set dirty to form field level
			var setDirty = function(field) {
				field.$dirty = true;
				field.$pristine = false;
			};
		  //clear data
		  var clearData = function(field) {
		  	field.$modelValue = '';
		  	field.$viewValue = '';
		  	field.$$lastCommittedViewValue = '';
		  }
		  // register popover event
		  var input_newpassword = element.find('input[name=newpassword]');
		  input_newpassword.popover({
		  	trigger: 'focus',
		  	container: 'body',
		  	title: 'Constraints',
		  	content: "The length must be between 6 to 20 characters. There should be at least one number and one letter of upper case or lower case."
		  });
		}
	}
}]).

directive('matchPassword', ['$parse', function($parse) {
	return {
		require: 'ngModel',
		link: function(scope, elem, attrs, ngModel) {
			scope.$watch(function() { 
				return $parse(attrs.matchPassword)(scope) === ngModel.$viewValue;
			}, function(currentValue) {
				ngModel.$setValidity('mismatch', currentValue);
        // fix a bug of AngularJS
        if(ngModel.$modelValue == undefined) scope.repeatpassword = ngModel.$$invalidModelValue;
      });
		}
	}
}]).

directive('leftPanel', ['$window', function($window) {
	return  {
		restrict: 'E',
		templateUrl: '/api/partials/left-panel',
		link: function(scope, elem, attr){
			var path = $window.location.pathname;
			var activeli = angular.element(elem).find('a[href="'+path+'"]').parent();
			activeli.addClass('active');

			angular.element(elem).find('a').click(function(event) {
				activeli.removeClass('active');
				activeli = angular.element(this).parent();
				activeli.addClass('active');
			})
		}
	}
}]).

directive('studentDetails', ['gradingSystem', function(gradingSystem) {
	return {
		restrict: 'E',
		scope: {
			student: '=',
			role: '='
		},
		templateUrl: '/api/partials/model-student-details',
		link: function(scope, elem, attr) {
			scope.grades = gradingSystem.getGrades;
			scope.GPA = gradingSystem.getGPA;
			scope.earnCredits = gradingSystem.getCredits;
		}
	}
}]).

directive('payment', ['$http', '$window', 'userService', '$rootScope',
	function($http, $window, userService, $rootScope) {
		return {
			restrict: 'E',
			scope: true,
			link: function(scope, elem, attr) {
				scope.user = userService.getUser;
				var form = scope.credit_card_form;
				var accountForm = scope.paypal_account_form;

				//set dirty to form field level
				var setDirty = function(field) {
					field.$dirty = true;
					field.$pristine = false;
				};

				scope.isPaymentComplete = false;
				scope.payment = '';
				scope.errorMessage = '';
				scope.errorMessages = [];

				scope.payByCreditCard = function() {
					setDirty(form.cardType);
					setDirty(form.amount);
					setDirty(form.firstname);
					setDirty(form.lastname);
					setDirty(form.cardNumber);
					setDirty(form.expireMonth);
					setDirty(form.expireYear);
					setDirty(form.cvv2);

					if(form.$valid) {
						$rootScope.$emit('loading-begin');

						$http.post('/api/payment/paypal/direct-credit-card', {
							amount: form.amount.$viewValue,
							type: form.cardType.$viewValue,
							firstName: form.firstname.$viewValue,
							lastName: form.lastname.$viewValue,
							number: form.cardNumber.$viewValue,
							expireMonth: form.expireMonth.$viewValue,
							expireYear: form.expireYear.$viewValue,
							cvv2: form.cvv2.$viewValue
						}).success(function(data, status) {
							$rootScope.$emit('loading-complete');
							userService.updateUser();
							scope.isPaymentComplete = true;
							scope.payment = data;
							scope.errorMessages = [];

							scope.cardType = '';
							scope.firstname = '';
							scope.lastname = '';
							scope.cardNumber = '';
							scope.expireMonth = '';
							scope.expireYear = '';
							form.$setPristine();
						}).error(function(data, status) {
							$rootScope.$emit('loading-complete');
							if(typeof data === 'string') {
								scope.errorMessages = [data];
							} else {
								scope.errorMessages = data;
							}
							
						});	
					}	
				}

				scope.payByPayPalAccount = function() {
					setDirty(form.amount);
					if(accountForm.$valid) {
						$rootScope.$emit('loading-begin');
						$http.post('/api/payment/paypal/paypal-account', form.amount.$viewValue
							).success(function(data, status) {
								$rootScope.$emit('loading-complete');
								scope.payment = data;
								scope.errorMessage = '';
								for(var i=0; i<data.links.length; i++) {
									if(data.links[i].rel == 'approval_url') {
										$window.open(data.links[i].href);
									}
								}

							}).error(function(data, status) {
								$rootScope.$emit('loading-complete');
								scope.errorMessage = data;
							});	
						}
					}
				}
			}
		}]).

directive('inquiry', ['$http', 'userService', 
	function($http, userService) {
		return {
			restrict: 'E',
			scope: true,
			link: function(scope, elem, attr) {
				var form = scope.inquiry_form;
				scope.isComplete = false;

			//set dirty to form field level
			var setDirty = function(field) {
				field.$dirty = true;
				field.$pristine = false;
			};

			scope.inquiry = function(event) {
				console.log('message');
				event.preventDefault();

				setDirty(form.title);
				setDirty(form.email);
				setDirty(form.content);

				if(form.$valid) {
					$http.post('/api/inquiry', {
						title: form.title.$viewValue,
						email: form.email.$viewValue,
						content: form.content.$viewValue,
					}).success(function(data, status) {
						scope.successMessage = data;
						scope.errorMessage = '';
						scope.isComplete = true;

						scope.title = '';
						scope.email = '';
						scope.content = '';
						form.$setPristine();
					}).error(function(data, status) {
						scope.errorMessage = data;
						scope.successMessage = '';
					});
				}
			}
		}
	}
}]);