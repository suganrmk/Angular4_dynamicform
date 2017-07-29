(function(angular) {
	var ebs;
	var ebsReportController = function($scope, moment, employeeAPIservice, ebsReportAPIservice){
		$scope.data = {};
	    $scope.stDate = {
	        opened: false
	    };
	    $scope.endDate = {
	        opened: false
	    };
	    $scope.reportStartDate = function() {
	        $scope.stDate.opened = true;
	    };
	    $scope.reportEndDate = function() {
	        $scope.endDate.opened = true;
	    };
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    		var errorMsg = '<p class="text-danger"></p>';
    		if($.trim($('#endDt').val()).length !=0  && new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY hh:mm a")).getFullYear() !=
	    		new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY hh:mm a")).getFullYear()){
				$('#endDt').parent().addClass('has-error');
				$(errorMsg).html('To Date must be as same year as From Date.').insertAfter($('#endDt'));
			}
    	};
	    
    	$scope.searchLeave = function(){
    		ebsReportAPIservice.loadEbsLeaveReport($scope.data).success(function(response) {
    			ebs.dtInstance.DataTable.clear().draw();
    			var dataArray;
    			$.each(response.entity, function(i, val){
    				response.entity[i].class
    			});
    			ebs.dtInstance.DataTable.rows.add(response.entity).draw();
	        });
	    };
	    
	    $scope.onDateChange = function(){
	    	if($scope.data.startsAt && $scope.data.endsAt){
	    		if($scope.data.startsAt.getTime() <= $scope.data.endsAt.getTime()){
	    			employeeAPIservice.loadAllEmployeeNamesByDate($scope.data.startsAt.getTime(), $scope.data.endsAt.getTime()).success(function(response) {
		    	        $scope.employees = response.entity;
		    	    });	
	    		}
	    	}
	    };
	};
	ebsReportController.$inject = ['$scope', 'moment', 'employeeAPIservice', 'ebsReportAPIservice'];
	mainApp.controller('ebsReportController', ebsReportController);
	
	function ebsReportControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, ebsReportAPIservice) {
		ebs = this;
		ebs.title = 'Ebs Reports';
		ebs.dtColumns = [
	        DTColumnBuilder.newColumn('firstName').withTitle('Employee Name').renderWith(function(data, type, full) {
	        	var employeeId = full.employeeId;
	        	if(employeeId == null)
	        		employeeId = full.ebsEmployeeId;
	        	var empName = '';
	        	if(full.lastName != null)
	        		empName =  full.lastName+', '+full.firstName+'('+employeeId+')';
	        	else
	        		empName =  full.ebsLastName+', '+full.ebsFirstName+'('+employeeId+')';
	        	if(full.appLeaveDate != full.ebsLeaveDate || full.appLeaveHours != full.ebsLeaveHours){
	        		return '<div style="color: red; font-weight: bold;">'+empName+'</div>';
	        	} else {
	        		return empName;
	        	}
	        }),
	        DTColumnBuilder.newColumn('appLeaveDate').withTitle('WFM Leave Date').withOption("type", "date").renderWith(function(data, type, full) {
	        	var date = '';
	        	if(full.appLeaveDate != null)
	        		date =  moment(full.appLeaveDate).format("DD-MMM-YYYY");
	        	if(full.appLeaveDate != full.ebsLeaveDate || full.appLeaveHours != full.ebsLeaveHours){
	        		return '<div style="color: red; font-weight: bold;">'+date+'</div>';
	        	} else{
	        		return date;
	        	}
	        }),
	        DTColumnBuilder.newColumn('requestType').withTitle('WFM Type').renderWith(function(data, type, full) {
	        	return full.requestType;
	        }),
	        DTColumnBuilder.newColumn('appLeaveHours').withTitle('Days').renderWith(function(data, type, full) {
	        	var hours = (full.appLeaveHours)/8;
	        	if(full.appLeaveHours != full.ebsLeaveHours || full.appLeaveDate != full.ebsLeaveDate)
	        		return '<div style="color: red; font-weight: bold;">'+hours+'</div>';
	        	else
	        		return hours;
	        }),
	        DTColumnBuilder.newColumn('ebsLeaveDate').withTitle('EBS Leave Date').withOption("type", "date").renderWith(function(data, type, full) {
	        	var date = '';
	        	if(full.ebsLeaveDate != null)
	        		date =  moment(full.ebsLeaveDate).format("DD-MMM-YYYY");
	        	if(full.appLeaveDate != full.ebsLeaveDate || full.appLeaveHours != full.ebsLeaveHours){
	        		return '<div style="color: red; font-weight: bold;">'+date+'</div>';
	        	} else{
	        		return date;
	        	}
	        }),
	        DTColumnBuilder.newColumn('ebsRequestType').withTitle('EBS Type').renderWith(function(data, type, full) {
	        	return full.ebsRequestType;
	        }),
	        DTColumnBuilder.newColumn('ebsLeaveHours').withTitle('EBS Days').renderWith(function(data, type, full) {
	        	var hours = (full.ebsLeaveHours)/8;
	        	if(full.appLeaveHours != full.ebsLeaveHours || full.appLeaveDate != full.ebsLeaveDate)
	        		return '<div style="color: red; font-weight: bold;">'+hours+'</div>';
	        	else
	        		return hours;
	        })
	    ];
	    var paramObj = {
	         "vm" : ebs,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : ebsReportAPIservice,
	         "sortCol" :1,
	         'actions': false
	    };
	    perfDatatable.loadTable.init(paramObj);
	}
	ebsReportControllerTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'ebsReportAPIservice'];
	mainApp.controller('ebsReportControllerTable', ebsReportControllerTable);
})(angular);