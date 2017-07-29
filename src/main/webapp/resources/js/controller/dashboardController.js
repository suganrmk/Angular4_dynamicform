(function(angular) {
	var holiday, reportee;
	var DashboardController = function($scope, $controller, leaveAPIservice, employeeAPIservice, currentUser,
			DTColumnBuilder , $compile, DTOptionsBuilder, $q, $timeout, holidayAPIservice){
		$scope.yearList = [];
		$scope.year = new Date().getFullYear();
		for(var i=2017; i <=$scope.year+1; i++){
			$scope.yearList.push({"year":i});
		}
		$scope.labels = moment.monthsShort();
		$scope.series = ['Leaves', 'WFH'];
		var user = currentUser.data.entity;
		$scope.loadChartData = function(){
			$scope.data = [];
			$scope.totalLeaveDays = 20;
			$scope.leavesTaken = 0;
			$scope.wfhAvailed = 0;
			$scope.leaveSplitdata = [];
			$scope.series = [];
			$scope.colorsByType = {"PTO": "#5cc394", "UNPLANNED_PTO": "#ffa860", "LOSS_OF_PAY": "#5cb85c", "MATERNITY_PAID_LEAVE": "#dd4b39", 
					"MATERNITY_UNPAID_LEAVE": "#3c8dbc", "WFH": "#696969", "MATERNITY_WFH":"#bce40d", "PATERNITY_WFH":"#f7ee2d"};
			$scope.colors = [];
			$scope.displayBalance = true;
			if(user.designations.designation == 'Subcontractor-Fixed' || user.designations.designation ==  'INTERN ADMIN' 
				|| user.designations.designation == 'INTERN CONSULTING')
				$scope.displayBalance = false;
			var loadLeavesByMonth = function(type){
				var deferred = $q.defer();
				var reqData = {"requestType":type,"startsAt":""+$scope.year+"-01-01T0:00:00.000Z","endsAt":""+$scope.year+"-12-31T18:30:00.000Z","empId":$scope.empId};
				leaveAPIservice.loadLeaveReport(reqData).success(function (response) {
					if(response.entity){
		    			var monthArr = [];
		    			var leaveType = [];
		    			$.each(response.entity, function(i, val){
		    				var month = moment(val[0]).format('MMM').toString();
		    				var type = val[2];
		    				if(leaveType.indexOf(type) == -1){
		    					leaveType.push(type);
		    					$scope.series.push(type.replace(/_/g, ' '));
		    					$scope.colors.push($scope.colorsByType[type]);
		    				}
		    			});
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
						deferred.resolve();
					}
		        });
				return deferred.promise;
			};
			
			$q.all([loadLeavesByMonth('ALL_PTO'),loadLeavesByMonth('ALL_WFH')]).then(function(){
				if($scope.series.length == 0){
					var ctx = $("#leaveWfhLineChart")[0].getContext('2d');
			        ctx.fillText("No Leave/WFH applied.", ctx.canvas.clientWidth / 6, ctx.canvas.clientHeight / 4);
				}
		    });
			
			leaveAPIservice.getLeaveBalanceByType('ALL_PTO', $scope.year, $scope.empId).success(function (response) {
				if(response.entity){
					$scope.leaveColors = [];
					$scope.leavelabels = [];
					$scope.leaveByType = [];
					if($.isEmptyObject(response.entity)){
						$timeout(function () {
							var ctx = $("#leaveChart")[0].getContext('2d');
					        ctx.fillText("No Leave applied.", ctx.canvas.clientWidth / 3, ctx.canvas.clientHeight / 2);
						}, 500);
					} else {
						$.each(response.entity, function(i, val){
							if(i === 'PTO' || i === 'UNPLANNED_PTO'){
								$scope.leavesTaken = $scope.leavesTaken + val;
							}
							$scope.leavelabels.push(i.replace(/_/g, ' '));
							$scope.leaveByType.push(val);
							$scope.leaveColors.push($scope.colorsByType[i]);
						});
						var joinDt = moment(joinDate).format("DD/MM/YYYY");
						if(moment(joinDt,"DD/MM/YYYY").year() === $scope.year){
				        	var month = 11-moment(joinDt,"DD/MM/YYYY").month();
				        	$scope.totalLeaveDays = round((month*1.67).toFixed(1));
				        }	
					}
				}
	        });
			
			leaveAPIservice.getLeaveBalanceByType('ALL_WFH', $scope.year, $scope.empId).success(function (response) {
				$scope.wfhlabels = [];
				$scope.wfhByType = [];
				$scope.wfhColors = [];
				if($.isEmptyObject(response.entity)){
					$timeout(function () {
						var ctx = $("#wfhChart")[0].getContext('2d');
				        ctx.fillText("No WFH applied.", ctx.canvas.clientWidth / 3, ctx.canvas.clientHeight / 2);
					}, 500);
				} else {
					$.each(response.entity, function(i, val){
						$scope.wfhAvailed += val;
						$scope.wfhlabels.push(i);
						$scope.wfhByType.push(val);
						$scope.wfhColors.push($scope.colorsByType[i]);
					});
				}
	        });
			
			leaveAPIservice.getEmpLeaveBalance($scope.year, $scope.empId).success(function (response) {
				$scope.carriedLeaves = response.entity['op_bal']/8;
				$scope.leaveBalance = response.entity['dec']/8;
				$scope.currentMonth = moment.monthsShort(moment().month());
				$scope.currentBal = response.entity[$scope.currentMonth.toLowerCase()]/8;
	    	});
			
			loadHolidays();
			loadReporters();
		};
		
		function loadReporters(empId){
			if($scope.empId)
				empId = $scope.empId;
			employeeAPIservice.loadEmployeesBySupervisor('0', empId, $scope.year).success(function (response) {
				reportee.dtInstance.DataTable.clear().draw();
				reportee.dtInstance.DataTable.rows.add(response.entity).draw();
		    });
		}
		
		function loadHolidays(){
			holidayAPIservice.getHolidaysByYear($scope.year).success(function (response) {
				holiday.dtInstance.DataTable.clear().draw();
				holiday.dtInstance.DataTable.rows.add(response.entity).draw();
		    });	
		}
		
		var joinDate = user.joinDate;
		var loadDashBoard = function(empId){
			if(empId){
				$scope.empId = empId;
				employeeAPIservice.loadByEmployeeById(empId).success(function (response) {
					joinDate = response.entity.joinDate;
					setUserData(response.entity);
					$scope.loadChartData();
			    });
			} else {
				$scope.empId = user.employeeId;
				joinDate = user.joinDate;
				$scope.loadChartData();
				setUserData(user);
			}
		}
		
		var setUserData = function(user){
			if($('#userData li').length == 0){
				$('#userData').html('<li id="'+user.employeeId+'"><a>'+user.lastName+', '+user.firstName+'</a></li>');	
			} else {
				$('#userData').append('<li id="'+user.employeeId+'"><a>'+user.lastName+', '+user.firstName+'</a></li>');
			}
		};
		
		$('#userData').on('click',  'li', function(){
			$scope.empId = $(this).attr('id');
			var index = $('#userData li').index($('#userData li[id="'+$scope.empId+'"]'));
	    	$('#userData li:gt('+index+')').remove();
			$scope.loadChartData();
			//loadReporters($scope.empId);
		});
		
		loadDashBoard();
		
		function round(value) {
	        return Math.round(value * 2) / 2;
	    }
		
		$('#reporteeTable table').on('click', 'tbody tr', function () {
			loadDashBoard($($(this).find('td:eq(0)').html()).attr('id'));
		});
	};
	DashboardController.$inject = ['$scope','$controller', 'leaveAPIservice', 'employeeAPIservice', 'currentUser',
	                               'DTColumnBuilder' , '$compile', 'DTOptionsBuilder', '$q', '$timeout', 'holidayAPIservice'];
	
	var holidayCalController = function($scope, $compile, DTOptionsBuilder, DTColumnBuilder) {
		holiday = this;
		holiday.title='Holiday';
		holiday.dtColumns = [
			DTColumnBuilder.newColumn('holidayDate').withTitle('Date').withOption("type", "date").renderWith(function(data, type, full) {
			    return moment(full.holidayDate).format("DD-MMM-YYYY");
			}),
	        DTColumnBuilder.newColumn('holidayName').withTitle('Holiday').renderWith(function(data, type, full) {
	            return full.holidayName;
	        })
	     ];
	     var paramObj = {
	         "vm" : holiday,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         'actions': false
	     };
	     perfDatatable.loadTable.init(paramObj);
	}
	holidayCalController.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder'];
	mainApp.controller('holidayCalController', holidayCalController);
	
	var reporteeController = function($scope, $compile, DTOptionsBuilder, DTColumnBuilder) {
		reportee = this;
		reportee.title='Reportee\'s';
		reportee.dtColumns = [
	        DTColumnBuilder.newColumn('firstName').withTitle('Name').renderWith(function(data, type, full) {
                return '<div id="'+full.employeeId+'">'+full.lastName+', '+full.firstName+'('+full.employeeId+')</div>';
            }),
            DTColumnBuilder.newColumn('designations.designation').withTitle('Designation')
	     ];
	     var paramObj = {
	         "vm" : reportee,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         'actions': false
	     };
	     perfDatatable.loadTable.init(paramObj);
	}
	reporteeController.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder'];
	mainApp.controller('reporteeController', reporteeController);

	mainApp.config(['ChartJsProvider', function (ChartJsProvider) {
	    // Configure all charts
	    ChartJsProvider.setOptions({
	      chartColors: ['#ffa860', '#5cc394', '#696969', '#5bc0de'],
	      responsive: true
	    });
	    // Configure all line charts
	    ChartJsProvider.setOptions('line', {
	      showLines: true
	    });
	  }]).controller('dashboardController', DashboardController);
})(angular);