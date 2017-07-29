(function(angular) {
	/* Project Controller*/
	var ProjectController = function($scope, $controller, $filter, DTColumnBuilder, roles){
		var _this = this;
		var role = roles.data.entity;
		$scope.role = role;
		//perfUtils.getInstance().validatePageAccess(role, 'prPg');
		$scope.stDate = {
	        opened: false
	    };
	    $scope.endDate = {
	        opened: false
	    };
	    $scope.prStartDate = function() {
	        $scope.stDate.opened = true;
	    };
	    $scope.prEndDate = function() {
	        $scope.endDate.opened = true;
	    };
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    	};
	    
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('projectName').withTitle('Projects'),
	        DTColumnBuilder.newColumn('startDate').withTitle('Start Date').withOption("type", "date").renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        }),
	        DTColumnBuilder.newColumn('endDate').withTitle('End Date').withOption("type", "date").renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        })
        ];
		
		var vm = {
			'title' : 'Project',
		    'formId' : 'projectForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addProject'],
		    'updateUrl' : perfUrl['updateProject'],
		    'deleteUrl' : perfUrl['deleteProject'],
		    'loadListUrl': perfUrl['loadProjects'],
		    'isAdd' : role.prSvBtn,
		    'deleteRow' : role.prDlBtn
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	ProjectController.$inject = ['$scope','$controller', '$filter', 'DTColumnBuilder', 'roles'];
	mainApp.controller('projectController', ProjectController);
})(angular);