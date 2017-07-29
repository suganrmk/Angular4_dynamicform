(function(angular) {
	var ticketRep;
	var ticketsReportController = function($scope, moment, employeeAPIservice, ticketService, roles){
		$scope.searchData = {};
		var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'tktRepPg');
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
	    
	    $scope.searchData.type = 'Open';
	    $scope.isSupport = true;
	    
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
    		ticketService.loadTicketsReport($scope.searchData).success(function(response) {
    			ticketRep.dtInstance.DataTable.clear().draw();
    			ticketRep.dtInstance.DataTable.rows.add(response.entity).draw();
	        });
	    };
	    
	    $(document).off('click', '#ticketReportTable table.dataTable tbody tr')
    		.on('click', '#ticketReportTable table.dataTable tbody tr', function () {
    		$scope.data = {};
    		$scope.data.ticket = $(this).find('td:eq(0)').html();
	    	if($scope.data.ticket !== 'No Records Found.'){
    			ticketService.loadTicketByTicketId($scope.data.ticket).success(function (response) {
    				$scope.data = response.entity;
    				$('#ticketForm').modal('show');
    			});
	    	}
	    });
	    
	    $scope.onDateChange = function(){
	    	if($scope.searchData.startsAt && $scope.searchData.endsAt){
	    		if($scope.searchData.startsAt.getTime() <= $scope.searchData.endsAt.getTime()){
	    			employeeAPIservice.loadAllEmployeeNamesByDate($scope.searchData.startsAt.getTime(), $scope.searchData.endsAt.getTime()).success(function(response) {
		    	        $scope.employees = response.entity;
		    	    });	
	    		}
	    	}
	    };
	};
	ticketsReportController.$inject = ['$scope', 'moment', 'employeeAPIservice', 'ticketService', 'roles'];
	mainApp.controller('ticketsReportController', ticketsReportController);
	
	function ticketReportControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, ticketService) {
		ticketRep = this;
		ticketRep.title = 'Tickets Reports';
		ticketRep.dtColumns = [
			DTColumnBuilder.newColumn('ticketNo').withTitle('Ticket'),
			DTColumnBuilder.newColumn('issueType').withTitle('Type'),
			DTColumnBuilder.newColumn('description').withTitle('Description').renderWith(function(data, type, full) {
				if(full.description.length <=20)
					return full.description;
				else
					return full.description.substring(0, 20)+'...';
			}),
			DTColumnBuilder.newColumn('priority').withTitle('Priority'),
			DTColumnBuilder.newColumn('status').withTitle('Status'),
			DTColumnBuilder.newColumn('employeeNamesView').withTitle('Created By').renderWith(function(data, type, full) {
	            return full.createdByView.lastName+', '+full.createdByView.firstName;
	        }),
			DTColumnBuilder.newColumn('employeeNamesView').withTitle('Assigned To').renderWith(function(data, type, full) {
	            return full.employeeNamesView.lastName+', '+full.employeeNamesView.firstName;
	        }),
	        DTColumnBuilder.newColumn('dtCreated').withTitle('Created On').withOption("type", "date").renderWith(function(data) {
				return moment(data).format("DD-MMM-YYYY");
	        }),
			DTColumnBuilder.newColumn('closedOn').withTitle('Closed On').withOption("type", "date").renderWith(function(data) {
				if(data == null)
					return '';
				else
					return moment(data).format("DD-MMM-YYYY");
	        })
	    ];
	    var paramObj = {
	         "vm" : ticketRep,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : ticketService,
	         "sortCol" :7,
	         'actions': false
	    };
	    perfDatatable.loadTable.init(paramObj);
	}
	ticketReportControllerTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'ticketService'];
	mainApp.controller('ticketReportControllerTable', ticketReportControllerTable);
})(angular);