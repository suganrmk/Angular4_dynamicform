(function(angular) {
/*Roles Management by employees Controller*/
	var EmpRolesController =  function($scope, $controller, rolesAPIservice, employeeAPIservice, 
			emprolesAPIservice, DTColumnBuilder , $compile, DTOptionsBuilder, roles) {
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'erPg');
		rolesAPIservice.getRolesDetails().success(function (response) {
	        $scope.roles = response.entity;
	    });

		$scope.data = {};
		$scope.data.employee = [];
		
		employeeAPIservice.loadAllEmployees().success(function (response) {
	        $scope.employeeList = response.entity;
	    });
		
	    $scope.manageRoles = function(){
	        emprolesAPIservice.saveEmpRoles($scope.data).success(function () {
	            perfUtils.getInstance().successMsg('Employee Roles Saved Successfully!');
	            $scope.onRoleChange();
	        });
	    };
	    
	    $scope.onRoleChange = function(){
	    	$scope.setComponentsDefault(jsonData);
	    	$scope.data.employee = [];
	    	emprolesAPIservice.loadEmpByRoles($scope.data.role.pk).success(function (response) {
	    		if(response.entity){
	    			$.each(response.entity, function(i, val){
		    			if($scope.data[i] !== undefined){
		    				$scope.data[i] = val;	
		    			}
		    		});
	    			$('input[key="prPg"], input[key="admMn"], input[key="finMn"], input[key="ptoPg"], input[key="wfhPg"]').prop('checked', true);
	    			$('input[key="prPg"], input[key="admMn"], input[key="finMn"], input[key="ptoPg"], input[key="wfhPg"]').prop('disabled', true);
	    		}
	        });
	    };
	    
	    $scope.applyRoles = function(){
	        emprolesAPIservice.saveEmpRoles($scope.data).success(function (response) {
	        	$scope.data = response.entity;
	        	perfUtils.getInstance().successMsg('Roles Applied Successfully!');
	        });
	    };
	    
	    var jsonData = '';
	    emprolesAPIservice.loadComponents().success(function (response) {
	    	jsonData = response;
	    	$scope.components = response;
	    	$scope.setComponentsDefault(response);
	    });
	    
	    $scope.setComponentsDefault = function(jsonData){
	    	$.each(jsonData, function(i, val){
		    	$scope.data[val.key] = false;
		    	if(val.child){
		    		$scope.setComponentsDefault(val.child);
		    	}
		    	if(val.btn){
		    		$scope.setComponentsDefault(val.btn);
		    	}
		    });
	    };
        
		$('#components').on('click', 'input[type="checkbox"]', function(){
			if($(this).prop('checked')){
				$.each($(this).parents('li').parents('li').children('div').find('input[type="checkbox"]'), function(i, val){
					$scope.data[$(this).attr('key')] = true;
					if(!$(this).prop('checked')){
						$(this).prop('checked', true);
					}
				});
			} else {
				$.each($(this).parents('li:eq(0)').children('ul').find('input[type="checkbox"]'), function(i, val){
					$(this).prop('checked', false);
					$scope.data[$(this).attr('key')] = false;
				});
			}
		});
	    
		$('#components').on('click', '.icon', function(){
			if($(this).hasClass('glyphicon-plus')){
				$(this).addClass('glyphicon-minus').removeClass('glyphicon-plus');
				$(this).siblings('ul').find('li span').addClass('glyphicon-minus').removeClass('glyphicon-plus');
				$(this).siblings('ul').find('li').show();
			} else {
				$(this).addClass('glyphicon-plus').removeClass('glyphicon-minus');
				$(this).siblings('ul').find('li').hide();
			}
		});
		
	    $scope.dtColumns = [
            DTColumnBuilder.newColumn('roleId').withTitle('Role Name').renderWith(function(data, type, full) {
                return data.roleName;
            }),
            DTColumnBuilder.newColumn('employee').withTitle('Employee').renderWith(function(data, type, full) {
                return data.lastName+', '+data.firstName;
            })
        ];
	    
        var paramObj = {
            "vm" : $scope,
            'title' : 'Employee Roles Management',
            "compile" : $compile,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : emprolesAPIservice,
            'sortCol': 1,
            'sEmptyTable' : 'Loading..'
        };
        perfDatatable.loadTable.init(paramObj);
	};
	
	EmpRolesController.$inject = ['$scope','$controller', 'rolesAPIservice', 'employeeAPIservice', 'emprolesAPIservice', 
	                              'DTColumnBuilder' , '$compile', 'DTOptionsBuilder', 'roles'];
	mainApp.controller('empRolesController', EmpRolesController);
})(angular);