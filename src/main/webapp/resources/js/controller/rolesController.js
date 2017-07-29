(function(angular) {
	/* Roles controller */
	var RolesController = function($scope, $controller, DTColumnBuilder, roles){
		var _this = this;
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'rlPg');
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('roleName').withTitle('Roles')
        ];
		
		var vm = {
			'title' : 'Role',
		    'formId' : 'rolesForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addRoles'],
		    'updateUrl' : perfUrl['updateRoles'],
		    'deleteUrl' : perfUrl['deleteRoles'],
		    'loadListUrl': perfUrl['loadRoles'],
		    'isAdd' : role.rlSvBtn,
		    'deleteRow' : role.rlDlBtn
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	RolesController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'roles'];
	mainApp.controller('rolesController', RolesController);
})(angular);