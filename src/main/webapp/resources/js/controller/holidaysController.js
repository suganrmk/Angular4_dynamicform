(function(angular) {
	/* Holidays Controller*/
	var HolidaysController = function($scope, $controller, DTColumnBuilder, roles){
		var role = roles.data.entity;
		$scope.role = role;
		
		perfUtils.getInstance().validatePageAccess(role, 'hdPg');
		
		$scope.holDt = {
	        opened: false
	    };

	    $scope.holDate = function() {
	        $scope.holDt.opened = true;
	    };
	    
	    $scope.yearList = [];
		$scope.year = new Date().getFullYear();
		for(var i=2017; i <=$scope.year+1; i++){
			$scope.yearList.push({"year":i});
		}
	    
	    $scope.validate = function(){console.log('uear ', $scope.data.year);
	    	if($.trim($scope.data.year).length !== 0 && $.trim($('#holidayDate').val()).length !==0){
	    		if(moment.utc($('#holidayDate').val(), "DD-MMM-YYYY").year() !== parseInt($scope.data.year)){
		    		var errorMsg = '<p class="text-danger"></p>';
		    		$('#holidayDate').parent().addClass('has-error');
		        	$(errorMsg).html('Holiday Date doesnt match with the year selected.').insertAfter($('#holidayDate'));
		    	} else if(moment.utc($('#holidayDate').val(), "DD-MMM-YYYY").day() == 0 
		    			|| moment.utc($('#holidayDate').val(), "DD-MMM-YYYY").day() == 6){
		    		var errorMsg = '<p class="text-danger"></p>';
		    		$('#holidayDate').parent().addClass('has-error');
		        	$(errorMsg).html('Holiday cannot be applied on a weekend').insertAfter($('#holidayDate'));
		    	}
	    	} 
	    };
		
		var _this = this;
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('year').withTitle('Year'),
            DTColumnBuilder.newColumn('holidayName').withTitle('Holiday Name'),
            DTColumnBuilder.newColumn('holidayDate').withTitle('Holiday Date').withOption("type", "date").renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        })
        ];
		
		var vm = {
			'title' : 'Holidays',
		    'formId' : 'holidaysForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addHoliday'],
		    'updateUrl' : perfUrl['updateHoliday'],
		    'deleteUrl' : perfUrl['deleteHoliday'],
		    'loadListUrl': perfUrl['loadHolidays'],
		    'isAdd' : role.hdSvBtn,
		    'deleteRow' : role.hdDlBtn
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	HolidaysController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'roles'];
	mainApp.controller('holidaysController', HolidaysController);
})(angular);