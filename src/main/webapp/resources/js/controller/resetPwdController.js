(function(angular) {
	/* Reset Password Controller*/
	var ResetPwdController = function($scope, $controller, currentUser, roles, employeeAPIservice){
		var _this = this;
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'rstpwdpg');
		
		employeeAPIservice.loadAllEmployeeByNames().success(function(response) {
	        $scope.employees = response.entity;
	    });
		
		$scope.resetPassword = function(){
			employeeAPIservice.resetPassword($scope.data.employeePk).success(function(response) {
				perfUtils.getInstance().successMsg('Password reset Successfully!');
		    });
		};
	};
	ResetPwdController.$inject = ['$scope','$controller', 'currentUser', 'roles', 'employeeAPIservice'];
	mainApp.controller('resetPwdController', ResetPwdController);
})(angular);