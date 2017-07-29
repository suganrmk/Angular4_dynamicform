(function(angular) {
	/* ProjectMembersController*/
	var ProjectMembersController = function($scope, $controller, DTColumnBuilder, projectAPIservice, 
			projectMemberAPIservice, employeeAPIservice, roles, $timeout){
		var _this = this;
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'pmPg');
		$scope.pmStDate = {
	        opened: false
	    };
	    $scope.pmEndDate = {
	        opened: false
	    };
	    $scope.pmStartDate = function() {
	        $scope.pmStDate.opened = true;
	    };
	    $scope.pmEndDate = function() {
	        $scope.pmEndDate.opened = true;
	    };
	    
	    projectAPIservice.loadProjects().success(function(response) {
	        $scope.projects = response.entity;
	    });

	    employeeAPIservice.loadAllEmployeeByNames().success(function(response) {
	        $scope.employees = response.entity;
	    });
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    	};
    	
    	$scope.updateProjMem = function(){
    		projectMemberAPIservice.updateProjectMember($scope.data).success(function (response) {
    			$('#projectMembersForm').modal('hide');
    			$scope.dtInstance.dataTable.fnUpdate(response.entity, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
    			perfUtils.getInstance().successMsg('Project Members updated Successfully!');
            });
    	};
    	
    	$scope.valUpdateDelete = function(){
    		perfUtils.getInstance().checkInactiveData($scope, $scope.employees,employeeAPIservice, 'employeeId', $timeout);
	    };
    	
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('projectId.projectName').withTitle('Project Name'),
	        DTColumnBuilder.newColumn('employeeView').withTitle('Employee Name').renderWith(function(data, type, full) {
	            return full.employeeView.lastName+', '+full.employeeView.firstName;
	        }),
	        DTColumnBuilder.newColumn('employeeView.designations.designation').withTitle('Designation'),
	        DTColumnBuilder.newColumn('dtStarted').withTitle('Start Date').withOption("type", "date").renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        }),
	        DTColumnBuilder.newColumn('dtEnded').withTitle('End Date').withOption("type", "date").renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        })
        ];
		
		var vm = {
			'title' : 'Project Members',
		    'formId' : 'projectMembersForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['saveProjectMember'],
		    'updateUrl' : perfUrl['updateProjectMember'],
		    'deleteUrl' : perfUrl['deleteProjectMember'],
		    'loadListUrl': perfUrl['loadAllProjectMembers'],
		    'isAdd' : role.pmSvBtn,
		    'deleteRow' : role.pmDlBtn
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	ProjectMembersController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'projectAPIservice', 'projectMemberAPIservice',
	                                    'employeeAPIservice', 'roles', '$timeout'];
	mainApp.controller('projectMembersController', ProjectMembersController);
})(angular);