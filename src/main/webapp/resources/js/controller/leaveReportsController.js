(function(angular) {
	var lr, splitList;
	var wfhReportController= function($scope, $controller, roles) {
		var role = roles.data.entity;
		perfUtils.getInstance().validatePageAccess(role, 'wfhRepPg');
	    $scope.title="WFH";
	    $scope.requestType="ALL_WFH";
	    angular.extend(this, $controller('leaveReportController', {
	        $scope: $scope
	    }));
	};
	wfhReportController.$inject = ['$scope','$controller', 'roles'];
	mainApp.controller('wfhReportController', wfhReportController);
	
	var ptoReportController = function($scope, $controller, roles) {
		var role = roles.data.entity;
		perfUtils.getInstance().validatePageAccess(role, 'ptoRepPg');
		$scope.title="Leave";
	    $scope.requestType="ALL_PTO";
	    $scope.isPto = true;
	    angular.extend(this, $controller('leaveReportController', {
	        $scope: $scope
	    }));
	};
	ptoReportController.$inject = ['$scope','$controller', 'roles'];
	mainApp.controller('ptoReportController', ptoReportController);
	
	var leaveReportController = function($scope, moment, leaveAPIservice, employeeAPIservice) {
	    $scope.data = {};
	    $scope.data.requestType = $scope.requestType;
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
	        leaveAPIservice.loadAllLeaveReport($scope.data).success(function(response) {
	            lr.dtInstance.DataTable.clear().draw();
	            lr.dtInstance.DataTable.rows.add(response.entity).draw();
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
	    
	    $(document).off('click', '#leaveReportTable table.dataTable tbody tr')
	    	.on('click', '#leaveReportTable table.dataTable tbody tr', function () {
	    	$scope.data.empId = $(this).find('td:eq(0)').html();
	    	$scope.data.empName = $(this).find('td:eq(1)').html();
	    	if($scope.data.empId !== 'No Records Found.'){
	    		leaveAPIservice.loadLeaveReport($scope.data).success(function(response) {
	    			if(response.entity){
		    			splitList.dtInstance.DataTable.clear().draw();
		    			var dataArr = [];
		    			var data = {"id":$scope.data.empId, "name": $scope.data.empName};
		    			var monthArr = [];
		    			var leaveType = [];
		    			$.each(response.entity, function(i, val){
		    				var month = moment(val[0]).format('MMM').toString();
		    				var type = val[2];
		    				if(leaveType.indexOf(type) == -1){
		    					leaveType.push(type);
		    				}
		    				if(!monthArr[month]){
		    					monthArr[month] = val[1]/8;
		    				} else {
		    					monthArr[month] = monthArr[month]+val[1]/8;
		    				}
		    			});
		    			data.jan = monthArr['Jan'];
		    			data.feb = monthArr['Feb'];
		    			data.mar = monthArr['Mar'];
		    			data.apr = monthArr['Apr'];
		    			data.may = monthArr['May'];
		    			data.jun = monthArr['Jun'];
		    			data.jul = monthArr['Jul'];
		    			data.aug = monthArr['Aug'];
		    			data.sep = monthArr['Sep'];
		    			data.oct = monthArr['Oct'];
		    			data.nov = monthArr['Nov'];
		    			data.dec = monthArr['Dec'];
		    			dataArr.push(data);
			        	splitList.dtInstance.DataTable.rows.add(dataArr).draw();
			            $('#leaveTableList').modal();
			            $scope.labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
			            $scope.series = leaveType;
			            $scope.leaveSplitdata = [];
			            $.each(leaveType, function(i, type){
			            	var monthArr = [];
			            	for(var mon=0; mon<= 11; mon++){
			            		monthArr[mon] = 0;
			            	}
			            	$.each(response.entity, function(j, val){
			            		if(type == val[2]){
			            			var month = moment(val[0]).month();
			            			if(!monthArr[month]){
				    					monthArr[month] = val[1]/8;
				    				} else {
				    					monthArr[month] = monthArr[month]+val[1]/8;
				    				}
			            		}
			            	});
			            	$scope.leaveSplitdata.push(monthArr);
			            });
	    			}
		        });
	    	}
	    });
	};
	leaveReportController.$inject = ['$scope', 'moment', 'leaveAPIservice', 'employeeAPIservice'];
	mainApp.controller('leaveReportController', leaveReportController);
	
	function leaveReportControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, leaveAPIservice) {
	    lr = this;
	    lr.title = $scope.title+' Reports';
	    lr.dtColumns = [
	        DTColumnBuilder.newColumn('employeeId').withTitle('Employee Id').withClass('actionsCol').renderWith(function(data, type, full) {
	        	if(full[13] == null || full[13] == "0" || $scope.title === 'WFH')
	        		return full[0];
	        	else
	        		return '<div style="color: red; font-weight: bold;">'+full[0]+'</div>';
	        }),
	        DTColumnBuilder.newColumn('employeeView').withTitle('Employee Name').withClass('actionsCol').renderWith(function(data, type, full) {
	        	//Active user
    			if(full[13] == null || full[13] == "0" || $scope.title === 'WFH')
    				return full[2]+', '+full[1];
    			else
    				return '<div style="color: red; font-weight: bold;">'+full[2]+', '+full[1]+'</div>';
	        })
	    ];
	    if($scope.title === 'Leave'){
	    	lr.dtColumns.push(
    			DTColumnBuilder.newColumn('opening_bal').withTitle('Carried Over').renderWith(function(data, type, full) {
    	        	return (full[9]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('PTO').renderWith(function(data, type, full) {
    	        	return (full[4]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('UnPlanned PTO').renderWith(function(data, type, full) {
    	        	return (full[5]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('Personal Leave').renderWith(function(data, type, full) {
    	        	return (full[6]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('Maternity Paid').renderWith(function(data, type, full) {
    	        	return (full[7]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('Maternity Unpaid').renderWith(function(data, type, full) {
    	        	return (full[8]/8);
    	        }),
    	        DTColumnBuilder.newColumn('hours').withTitle('Current Period Availed').renderWith(function(data, type, full) {
    	            return ((full[4]+full[5]+full[6]+full[7]+full[8])/8);
    	        }),
    	        DTColumnBuilder.newColumn('hours').withTitle('LOP Adjustments').renderWith(function(data, type, full) {
    	            return (full[12]/8);
    	        }),
	    		DTColumnBuilder.newColumn('balance').withTitle('Leave Balance').renderWith(function(data, type, full) {
	    			//Active user
	    			if(full[13] == null || full[13] == "0")
	    				return ((full[10]/8)+(full[11]/8));
	    			else
	    				return 0;
	    		})
	    	);
	    }
	    if($scope.title === 'WFH'){
	    	lr.dtColumns.push(
    			DTColumnBuilder.newColumn('balance').withTitle('WFH').renderWith(function(data, type, full) {
    	        	return (full[4]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('MATERNITY WFH').renderWith(function(data, type, full) {
    	        	return (full[5]/8);
    	        }),
    	        DTColumnBuilder.newColumn('balance').withTitle('PATERNITY WFH').renderWith(function(data, type, full) {
    	        	return (full[6]/8);
    	        }),
    	        DTColumnBuilder.newColumn('hours').withTitle('Current Period Availed').renderWith(function(data, type, full) {
    	            return ((full[4]+full[5]+full[6])/8);
    	        })
	    	);
	    }
	    var paramObj = {
	         "vm" : lr,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         "sortCol" :1,
	         'editFormId' : 'leaveModal',
	         'actions': false
	    };
	    perfDatatable.loadTable.init(paramObj);
	}
	leaveReportControllerTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'leaveAPIservice'];
	mainApp.controller('leaveReportControllerTable', leaveReportControllerTable);
	
	function leaveSplitUpControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, leaveAPIservice) {
		splitList = this;
		splitList.title = $scope.title+' Reports';
		splitList.dtColumns = [
	        DTColumnBuilder.newColumn('id').withTitle('Employee Id').renderWith(function(data, type, full) {
	            return data;
	        }),
	        DTColumnBuilder.newColumn('name').withTitle('Name').renderWith(function(data, type, full) {
	            return data;
	        }),
	        DTColumnBuilder.newColumn('jan').withTitle('Jan').renderWith(function(data, type, full) {
	            return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('feb').withTitle('Feb').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('mar').withTitle('Mar').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('apr').withTitle('Apr').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('may').withTitle('May').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('jun').withTitle('Jun').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('jul').withTitle('Jul').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('aug').withTitle('Aug').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('sep').withTitle('Sep').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('oct').withTitle('Oct').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('nov').withTitle('Nov').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        }),
	        DTColumnBuilder.newColumn('dec').withTitle('Dec').renderWith(function(data, type, full) {
	        	return data == undefined? 0:data;
	        })
	     ];
	     var paramObj = {
	         "vm" : splitList,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         'editFormId' : 'leaveModal',
	         'actions': false
	     };
	     perfDatatable.loadTable.init(paramObj);
	};
	leaveSplitUpControllerTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'leaveAPIservice'];
	mainApp.controller('leaveSplitUpControllerTable', leaveSplitUpControllerTable);
})(angular);