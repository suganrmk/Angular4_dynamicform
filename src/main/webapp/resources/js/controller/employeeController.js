(function(angular) {
	/* Employee controller */
	var EmployeeController = function($scope, $controller, employeeAPIservice, DTColumnBuilder , $compile, DTOptionsBuilder, roles) {
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'empPg');
		$scope.loadEmployees = function(){
			employeeAPIservice.loadEmployees().success(function (response) {
		        $scope.employees = response.entity;
		        $scope.dtInstance.DataTable.clear().draw();
		        $scope.dtInstance.DataTable.rows.add($scope.employees).draw();
		    });
		};
		$scope.loadEmployees();
	    $scope.addEmployee = function(){
	    	$scope.data = {};
	    	perfUtils.getInstance().resetForm();
	        $('#employeeForm').modal();
	    };
	
	    $scope.save = function(){
	        employeeAPIservice.addEmployee($scope.data).success(function (){
	        	perfUtils.getInstance().successMsg('Employee Saved Successfully!');
	        	$scope.loadEmployees();
	            $('#employeeForm').modal('hide');
	        });
	    };
	    
	    $scope.valUpdateDelete = function(){
	    	if($scope.data.flag && $scope.data.lastWorkDate == null){
	    		$scope.isGeneratePwd = true;
	    	} else {
	    		$scope.isGeneratePwd = false;
	    	}
	    };
	    
	    $scope.update = function(){
	        employeeAPIservice.updateEmployee($scope.data).success(function (){
	        	$scope.dtInstance.dataTable.fnUpdate($scope.data, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
	            perfUtils.getInstance().successMsg('Employee updated Successfully!');
	            $('#employeeForm').modal('hide');
	        });
	    };
	    
	    $scope.generateCrendentials = function(){
	    	$scope.data.flag = false;
	    	employeeAPIservice.generateCrendentials($scope.data.pk).success(function (){
	    		$scope.dtInstance.dataTable.fnUpdate($scope.data, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
	        	perfUtils.getInstance().successMsg('Credentials Created Successfully!');
	            $('#employeeForm').modal('hide');
	        });
	    	
	    };
	
	    $scope.dtColumns = [
            DTColumnBuilder.newColumn('employeeId').withTitle('Employee Id'),
            DTColumnBuilder.newColumn('firstName').withTitle('Name').renderWith(function(data, type, full) {
                return full.lastName+', '+full.firstName;
            }),
            DTColumnBuilder.newColumn('currentAddress').withTitle('Current Address').notVisible().renderWith(function(data, type, full) {
                return full.currentAddress;
            }),
            DTColumnBuilder.newColumn('city').withTitle('Current City').notVisible().renderWith(function(data, type, full) {
                return full.city;
            }),
            DTColumnBuilder.newColumn('permanentAddress').withTitle('Permanent Address').notVisible().renderWith(function(data, type, full) {
                return full.permanentAddress;
            }),
            DTColumnBuilder.newColumn('contactNo').withTitle('Contact No').notVisible().renderWith(function(data, type, full) {
                return full.contactNo;
            }),
            DTColumnBuilder.newColumn('emergencyContactNo').withTitle('Emergency Contact No').notVisible().renderWith(function(data, type, full) {
                return full.emergencyContactNo;
            }),
            DTColumnBuilder.newColumn('bloodGroup').withTitle('BloodGroup').notVisible().renderWith(function(data, type, full) {
                return full.bloodGroup;
            }),
            DTColumnBuilder.newColumn('gender').withTitle('Gender').notVisible().renderWith(function(data, type, full) {
                return full.gender;
            }),
            DTColumnBuilder.newColumn('joinDate').withTitle('Joined Date').withOption("type", "date").notVisible().renderWith(function(data, type, full) {
                return moment(full.joinDate).format("DD-MMM-YYYY");
            }),
            DTColumnBuilder.newColumn('panCard').withTitle('Pancard').notVisible().renderWith(function(data, type, full) {
                return full.panCard;
            }),
            DTColumnBuilder.newColumn('superviserFirstName').withTitle('Supervisor').notVisible().renderWith(function(data, type, full) {
                return full.superviserLastName+', '+full.superviserFirstName;
            }),
            DTColumnBuilder.newColumn('email').withTitle('Email'),
            DTColumnBuilder.newColumn('designations.designation').withTitle('Designation'),
            DTColumnBuilder.newColumn('lastWorkDate').withTitle('Last Working Date').withOption("type", "date").renderWith(function(data) {
            	if(data == null){
            		return '';
            	} else
            		return moment(data).format("DD-MMM-YYYY");
	        })
        ];
	    
	    $scope.title = 'Employees';
        var paramObj = {
            "vm" : $scope,
            "compile" : $compile,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : employeeAPIservice,
            'editFormId' : 'employeeForm',
            'sortCol': 1,
            'sEmptyTable' : 'Loading..',
            "deleteRow" : false
        };
        perfDatatable.loadTable.init(paramObj);

	    angular.extend(this, $controller('empProfileController', {
	        $scope: $scope
	    }));
	};
	EmployeeController.$inject = ['$scope','$controller', 'employeeAPIservice', 'DTColumnBuilder' , '$compile', 'DTOptionsBuilder', 'roles'];
	mainApp.controller('employeeController', EmployeeController);
})(angular);