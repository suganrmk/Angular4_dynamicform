(function(angular) {
	var SupportController = function($scope, $controller, DTColumnBuilder, employeeAPIservice, roles, currentUser) {
	    var scope = this;
	    var role = roles.data.entity;
		$scope.role = role;
		perfUtils.getInstance().validatePageAccess(role, 'sdPg');
	    $scope.title = 'Support Tickets';
	    $scope.loadUrl = perfUrl['loadAssignedTickets'];
	    $scope.isAdd = false;
	    $scope.isSupport = true;
	    $scope.currentUserPk = currentUser.data.entity.pk;
	    $scope.closeDate = {
	        opened: false
	    };
	    
	    $scope.tkCloseDate = function() {
	        $scope.closeDate.opened = true;
	    };
	    
	    employeeAPIservice.loadAllEmployeeByNames().success(function(response) {
	        $scope.employees = response.entity;
	    });
	    
	    $scope.dtColumns = [
			DTColumnBuilder.newColumn('ticketNo').withTitle('Ticket'),
			DTColumnBuilder.newColumn('location').withTitle('Location'),
			DTColumnBuilder.newColumn('issueType').withTitle('Type'),
			DTColumnBuilder.newColumn('description').withTitle('Description').renderWith(function(data, type, full) {
				if(full.description.length <=10)
					return full.description;
				else
					return full.description.substring(0, 10)+'...';
			}),
			DTColumnBuilder.newColumn('priority').withTitle('Priority'),
			DTColumnBuilder.newColumn('status').withTitle('Status'),
			DTColumnBuilder.newColumn('employeeNamesView').withTitle('Created By').renderWith(function(data, type, full) {
	            return full.createdByView.lastName+', '+full.createdByView.firstName;
	        }),
			DTColumnBuilder.newColumn('employeeNamesView').withTitle('Assigned To').renderWith(function(data, type, full) {
	            return full.employeeNamesView.lastName+', '+full.employeeNamesView.firstName;
	        }),
			DTColumnBuilder.newColumn('closedOn').withTitle('Closed On').withOption("type", "date").renderWith(function(data) {
				if(data == null)
					return '';
				else
					return moment(data).format("DD-MMM-YYYY");
	        })
	    ];
	    
	    angular.extend(this, $controller('supportTicketController', {
	        $scope: $scope
	    }));
	};
	SupportController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'employeeAPIservice', 'roles', 'currentUser'];
	mainApp.controller('supportController', SupportController);
	
	var TicketController = function($scope, $controller, DTColumnBuilder, ticketService, currentUser){
	    var scope = this;
	    $scope.title = 'My Tickets';
	    $scope.isAdd = true;
	    $scope.loadUrl = perfUrl['loadTickets'];
	    $scope.currentUserPk = currentUser.data.entity.pk;
	    $scope.dtColumns = [
			DTColumnBuilder.newColumn('ticketNo').withTitle('Ticket'),
			DTColumnBuilder.newColumn('location').withTitle('Location'),
			DTColumnBuilder.newColumn('issueType').withTitle('Type'),
			DTColumnBuilder.newColumn('description').withTitle('Description').renderWith(function(data, type, full) {
				if(full.description.length <=10)
					return full.description;
				else
					return full.description.substring(0, 10)+'...';
			}),
			DTColumnBuilder.newColumn('status').withTitle('Status'),
			DTColumnBuilder.newColumn('closedOn').withTitle('Closed On').withOption("type", "date").renderWith(function(data) {
				if(data == null)
					return '';
				else
					return moment(data).format("DD-MMM-YYYY");
	        })
	    ];
	    
	    $scope.saveTicket = function(){
	    	var file = $scope.uploadTicketDoc;
	    	$scope.validateFile();
	    	ticketService.saveTicket(file, $scope.data).success(function(data){
	    		perfUtils.getInstance().successMsg('Ticket Saved Successfully!');
	    		$scope.dtInstance.reloadData();
	    		$('#ticketForm').modal('hide');
	        });
	    };
	    
	    angular.extend(this, $controller('supportTicketController', {
	        $scope: $scope
	    }));
	};
	TicketController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'ticketService', 'currentUser'];
	mainApp.controller('ticketController', TicketController);
	
	/*Support Ticket Controller*/
	var SupportTicketController = function($scope, $controller, DTColumnBuilder, ticketService, $timeout){
		var _this = this;
		
		$scope.isUpdate = true;
		$scope.validateFile = function(){
			var file = $scope.uploadTicketDoc;
			if(file){
				//2mb file size
		    	if((file.size /1024) > 2048){
		    		perfUtils.getInstance().errorMsg('File size is greater than 2MB!');
		    		return false;
		    	}	
			}
		};
		
		$scope.valUpdateDelete = function(){
    		perfUtils.getInstance().checkInactiveData($scope, $scope.employees,employeeAPIservice, 'employeeId', $timeout);
	    };
		
		$scope.validate = function(){
			var errorMsg = '<p class="text-danger"></p>';
			if($.trim($('#closedOn').val()).length == 0 && $scope.data.status == 'Closed'){
	    		$('#closedOn').parent().addClass('has-error');
	        	$(errorMsg).html('Closed Date must be provided when the status is closed.').insertAfter($('#closedOn'));
	        	return false;
	    	} else if($.trim($('#closedOn').val()).length != 0 && $scope.data.status != 'Closed'){
	    		$('#closedOn').parent().addClass('has-error');
	        	$(errorMsg).html('Closed Date should be empty if the status is not closed.').insertAfter($('#closedOn'));
	        	return false;
	    	}
		};
		
		$scope.valUpdateDelete=function(){
			//reset file value
			$('form').find('.inputfile').val('');
			$scope.uploadTicketDoc = null;
		};
		
		$scope.updTicket = function(){
			var file = $scope.uploadTicketDoc;
			$scope.validateFile();
			ticketService.updateTicket(file, $scope.data).success(function (response) {
				$('#ticketForm').modal('hide');
				$scope.dtInstance.dataTable.fnUpdate(response.entity, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
				perfUtils.getInstance().successMsg('Ticket updated Successfully!');
	        });
		};
		
		$scope.deleteAttachment = function(id, ticketId){
			perfUtils.getInstance().confirmPopMsg('Are you sure you want to delete the attachment?').then(function(val){
				if(val){
					ticketService.deleteAttachment(id).success(function (response) {
						$('#attach-'+id).remove();
						$scope.loadTicketsById(ticketId);
						perfUtils.getInstance().successMsg('Attachment deleted Successfully!');
					});
				}
			});
		};
		
		$scope.deleteComment = function(id, ticketId){
			perfUtils.getInstance().confirmPopMsg('Are you sure you want to delete the comment?').then(function(val){
				if(val){
					ticketService.deleteComment(id).success(function (response) {
						$('#cmt-'+id).remove();
						$scope.loadTicketsById(ticketId);
						perfUtils.getInstance().successMsg('Comment deleted Successfully!');
					});
				}
			});
		};
		
		$scope.loadTicketsById = function(id){
			ticketService.loadTicketsById(id).success(function (response) {
				$scope.data = response.entity;
				$scope.dtInstance.dataTable.fnUpdate(response.entity, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
			});
		};
		
		var vm = {
			'title' : $scope.title,
		    'formId' : 'ticketForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addTicket'],
		    'updateUrl' : perfUrl['updateTicket'],
		    'deleteUrl' : perfUrl['deleteTicket'],
		    'loadListUrl': $scope.loadUrl,
		    'isAdd' : $scope.isAdd,
		    'deleteRow' : true
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	SupportTicketController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'ticketService', '$timeout'];
	mainApp.controller('supportTicketController', SupportTicketController);
})(angular);