(function(angular) {
	/* Designation Controller*/
	var DesignationController = function($scope, $controller, DTColumnBuilder, currentUser, roles){
		var _this = this;
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'jtPg');
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('designation').withTitle('Designation'),
            DTColumnBuilder.newColumn('sbu').withTitle('SBU')
        ];
		
		var vm = {
			'title' : 'Designation',
		    'formId' : 'designationForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addDesignation'],
		    'updateUrl' : perfUrl['updateDesignation'],
		    'deleteUrl' : perfUrl['deleteDesignation'],
		    'loadListUrl': perfUrl['loadDesignations'],
		    'isAdd' : role.jtSvBtn,
		    'deleteRow' : role.jtDlBtn
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	DesignationController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'currentUser', 'roles'];
	mainApp.controller('designationController', DesignationController);
})(angular);