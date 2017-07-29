(function(angular) {
	var WfhController = function($scope, $controller) {
	    var scope = this;
	    scope.events = [];
	    $scope.title="WFH";
	    $scope.leaveType="WFH";
	    $scope.btnTheme = "btn-danger";
	    angular.extend(this, $controller('leaveController', {
	        $scope: $scope
	    }));
	};
	WfhController.$inject = ['$scope','$controller'];
	mainApp.controller('wfhController', WfhController);
	
	var PtoController = function($scope, $controller){
	    var scope = this;
	    scope.events = [];
	    $scope.title="Leave";
	    $scope.leaveType="PTO";
	    $scope.isPto = true;
	    $scope.btnTheme = "btn-success";
	    angular.extend(this, $controller('leaveController', {
	        $scope: $scope
	    }));
	};
	PtoController.$inject = ['$scope','$controller'];
	mainApp.controller('ptoController', PtoController);
	
	/**
	 * AngularJS default filter with the following expression:
	 * "person in people | filter: {name: $select.search, age: $select.search}"
	 * performs an AND between 'name: $select.search' and 'age: $select.search'.
	 * We want to perform an OR.
	 */
	mainApp.filter('propsFilter', function() {
	  return function(items, props) {
	    var out = [];
	    if (angular.isArray(items)) {
	      var keys = Object.keys(props);
	      items.forEach(function(item) {
	        var itemMatches = false;
	        for (var i = 0; i < keys.length; i++) {
	          var prop = keys[i];
	          var text = props[prop].toLowerCase();
	          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
	            itemMatches = true;
	            break;
	          }
	        }
	        if (itemMatches) {
	          out.push(item);
	        }
	      });
	    } else {
	      // Let the output be the input untouched
	      out = items;
	    }
	    return out;
	  };
	});
	
	var LeaveController = function($scope, $rootScope, moment, $filter, user, leaveAPIservice, employeeAPIservice, calendarConfig, 
			DTColumnBuilder, $compile, DTOptionsBuilder, profileAPIservice, $q, holidayAPIservice, $timeout, emprolesAPIservice) {
	    var obj = this;
	    //These variables MUST be set as a minimum for the calendar to work
	    obj.calendarView = 'month';
	    obj.viewDate = new Date();
	    obj.viewChangeEnabled = true;
	    obj.checkLeaves = 'mine';
	    obj.calYear = new Date(obj.viewDate).getFullYear();
	    obj.calMonth = new Date(obj.viewDate).getMonth();
	    scope = $scope;
	    $scope.data = {};
	    $scope.empLeaveBalance = {};
	    $scope.employees = [];
	    $scope.employeesList;
	    $scope.leaveBalance;
	    $scope.data.requestType = $scope.leaveType;
	    $scope.leaveYear = obj.calYear;
	    var eventArr = [];
	    calendarConfig.displayEventEndTimes = true;
	    calendarConfig.templates.calendarSlideBox = 'html/templates/calendarSlideBoxTemplate.html';
	    calendarConfig.templates.calendarMonthCell = 'html/templates/calendarMonthCell.html';
	    calendarConfig.templates.calendarMonthCellEvents = 'html/templates/calendarMonthCellEvents.html';
	    calendarConfig.templates.calendarYearView = 'html/templates/calendarYearView.html';
	    var ptoLeaveType = {"PTO":{"key":"PTO",'val': "PTO"}, 
	                        "UNPLANNED_PTO" : {"key":"UNPLANNED_PTO",'val':'UNPLANNED PTO'},
	                        "LOSS_OF_PAY": {"key":"LOSS_OF_PAY",'val': "PERSONAL LEAVE(LOP)"},
	                        "MATERNITY_PAID_LEAVE" : {"key":"MATERNITY_PAID_LEAVE",'val': "MATERNITY PAID LEAVE"},
	                        "MATERNITY_UNPAID_LEAVE" : {"key":"MATERNITY_UNPAID_LEAVE", 'val': "MATERNITY UNPAID LEAVE"}
	                        };
		var wfhLeaveType = {
				"WFH":{"key":"WFH", "val":"WFH"},
				"MATERNITY_WFH": {"key":"MATERNITY_WFH", "val":"MATERNITY WFH"},
				"PATERNITY_WFH":{"key":"PATERNITY_WFH", "val":"PATERNITY WFH"}
		};
	    var loadLeaveTypeList = function(){
	    	var leaveTypeList = [];
	    	var gender = user.loggedUser.gender;
	    	if($scope.leaveType === 'PTO'){
	    		leaveTypeList.push(ptoLeaveType.PTO);
	    		leaveTypeList.push(ptoLeaveType.UNPLANNED_PTO);
	    		leaveTypeList.push(ptoLeaveType.LOSS_OF_PAY);
	    		leaveTypeList.push(ptoLeaveType.MATERNITY_PAID_LEAVE);
	    		leaveTypeList.push(ptoLeaveType.MATERNITY_UNPAID_LEAVE);
		    } else {
		    	leaveTypeList.push(wfhLeaveType.WFH);
	    		leaveTypeList.push(wfhLeaveType.MATERNITY_WFH);
	    		leaveTypeList.push(wfhLeaveType.PATERNITY_WFH);
		    }
	    	$scope.leaveTypeList = leaveTypeList;	
	    }
	    
	    var roles;
	    
	    loadLeaveTypeList();
	    var startVal = moment().subtract(1, 'month');
    	var endVal = moment().add(10, 'month');
	
	    this.toggle = function($event, field, event) {
	        $event.preventDefault();
	        $event.stopPropagation();
	        event[field] = !event[field];
	    };
	
	    this.timeSpanClicked = function(calendarCell, $event){
	        if(calendarCell.events.length > 0){
	        	$rootScope.$broadcast('loading:progress');
	        	obj.calMonth = moment(calendarCell.date).month();
	        	$scope.dtInstance.DataTable.clear().draw();
	        	var dataArr = [];
	        	$.each(calendarCell.events, function(i, val){
	        		if($scope.leaveType === 'PTO'){
	        			if(!roles.ptoDlBtn){
		        			val['isDel'] = false;	
		        		}
	        		} else {
	        			if(!roles.wfhDlBtn){
		        			val['isDel'] = false;	
		        		}
	        		}
	        		//exclude holidays in modal
	        		if(val.employeeId)
	        			dataArr.push(val);
	        	});
	        	if(dataArr.length > 0){
	        		$scope.dtInstance.DataTable.rows.add(dataArr).draw();
		            $('#leaveList').modal();
	        	}
	        	$rootScope.$broadcast('loading:finish');
	        }
	    };
	    
	    $scope.valUpdateDelete = function(){
	    	if($scope.leaveType === 'PTO'){
	    		$scope.isUpdate = (!roles['ptoUpBtn'])?false:true;
    		} else {
    			$scope.isUpdate = (!roles['wfhUpBtn'])?false:true;
    		}
	    	isReqTypeDisabled($scope.data.employeeId, $scope.data);
	    	perfUtils.getInstance().checkInactiveData($scope, $scope.employees, employeeAPIservice, 'employeeId', $timeout);
	    }
	    
	    var isReqTypeDisabled = function(empPk, data){
	    	//loadLeaveTypeList();
	    	$scope.leaveTypeList = [];
	    	$scope.isTypeDisabled = false;
    		if($scope.leaveType === 'PTO'){
    			if(data){
    				if(roles['ptoUpBtn'] 
		    				&& (data.requestType === 'PTO' || data.requestType === 'UNPLANNED_PTO')){
		    			$scope.leaveTypeList.push(ptoLeaveType.PTO);
		    			$scope.leaveTypeList.push(ptoLeaveType.UNPLANNED_PTO);
		    		} else {
		    			$scope.isTypeDisabled = true;
		    			$scope.leaveTypeList.push(ptoLeaveType[data.requestType]);
		    		}
    			} else {
    				var setLeaveType = function(user){
    					if(user.designations.designation == 'Subcontractor-Fixed' || user.designations.designation == 'INTERN ADMIN'
    		    			|| user.designations.designation == 'INTERN CONSULTING'){
    		    			$scope.isTypeDisabled = true;
//    		    			$scope.data.requestType = '';
    		    			$scope.leaveTypeList.push(ptoLeaveType.LOSS_OF_PAY);
    		    		} else if(user.gender == 'Male'){
    		    			$scope.leaveTypeList.push(ptoLeaveType.PTO);
    		    			$scope.leaveTypeList.push(ptoLeaveType.UNPLANNED_PTO);
    		    			$scope.leaveTypeList.push(ptoLeaveType.LOSS_OF_PAY);
    		    		} else if(user.gender == 'Female'){
    		    			$scope.leaveTypeList.push(ptoLeaveType.PTO);
    		    			$scope.leaveTypeList.push(ptoLeaveType.UNPLANNED_PTO);
    		    			$scope.leaveTypeList.push(ptoLeaveType.LOSS_OF_PAY);
    		    			$scope.leaveTypeList.push(ptoLeaveType.MATERNITY_PAID_LEAVE);
    		    			$scope.leaveTypeList.push(ptoLeaveType.MATERNITY_UNPAID_LEAVE);
    		    		}
    				}
    				if($scope.data.employeeId && user.loggedUser.pk != $scope.data.employeeId){
    					employeeAPIservice.loadById($scope.data.employeeId).success(function (response) {
    						setLeaveType(response.entity);
    				    });
    				} else {
    					setLeaveType(user.loggedUser);
    				}
    			}
    		} else {
//    			$scope.leaveTypeList = [];
    			var setLeaveType = function(user){
    				if(user.gender == 'Male'){
    					$scope.leaveTypeList.push(wfhLeaveType.WFH);
    					$scope.leaveTypeList.push(wfhLeaveType.PATERNITY_WFH);
        			} else {
        				$scope.leaveTypeList.push(wfhLeaveType.WFH);
        				$scope.leaveTypeList.push(wfhLeaveType.MATERNITY_WFH);
        			}	
    			}
    			if($scope.data.employeeId && user.loggedUser.pk != $scope.data.employeeId){
					employeeAPIservice.loadById($scope.data.employeeId).success(function (response) {
						setLeaveType(response.entity);
				    });
				} else {
					setLeaveType(user.loggedUser);
				}
    		}
	    }
	    
	    $scope.isUpdateDel = function(data){
	    	if(data.employeeId != user.loggedUser.pk 
	    			|| !((startVal.month() <= obj.calMonth && startVal.year() === obj.calYear) 
	    	    			|| (endVal.month() >= obj.calMonth && endVal.year() === obj.calYear))){
	    		return false;
	    	}
	    	return true;
	    };
	    
	    this.viewChangeClicked = function(date){
	    	//event fired when clicking month from year view
	    	obj.calMonth = moment(date).month();
	    	obj.calendarView = 'month';
	    	$scope.togglePrevNxt(date);
	    };

	    $scope.showHideApply = function(){
	    	//$('#applyLeave').hide();
	    	//Show the add button only when the date ranges were applicable
	    	if((startVal.month() <= obj.calMonth && startVal.year() === obj.calYear) 
	    			|| (endVal.month() >= obj.calMonth && endVal.year() === obj.calYear)){
	    		$('#applyLeave').show();
	    	}
	    };
	    
	    $scope.togglePrevNxt = function(calendarDate){
            obj.calYear = new Date(calendarDate).getFullYear();
	        obj.calMonth = new Date(calendarDate).getMonth();
	        $scope.toggleLeave(obj.checkLeaves, obj.calMonth);
	    };
	    
	    $scope.yearView = function(calendarDate){
	    	obj.calendarView = 'year';
	    	$scope.togglePrevNxt(calendarDate);
	    };
	    
	    $scope.monthView = function(calendarDate){
	    	obj.calendarView = 'month';
	    	$scope.togglePrevNxt(calendarDate);
	    };
	    
	    $scope.toggleLeave = function(val, month){
	    	$scope.showHideApply();
	    	//Reset events on toggle.... 
	    	$scope.scope.events.splice(0, $scope.scope.events.length);
	        if(val === 'all'){
	        	if(obj.calendarView === 'month'){
	        		//set month while switching back from Mine to All in month view
	        		if(!month)
	        			month = obj.calMonth;
	        		leaveAPIservice.loadLeavesByMonth($scope.leaveType, obj.calYear, month+1).success(function (response) {
		                $scope.displayLeave(response.entity);
		            });
	        	} else {
	        		leaveAPIservice.loadLeavesByYear($scope.leaveType, obj.calYear).success(function (response) {
		                $scope.displayLeave(response.entity);
		            });
	        	}
	        } else {
	        	//if(val !== obj.checkLeaves || $scope.leaveYear !== obj.calYear){
	        		leaveAPIservice.loadMyLeaves($scope.leaveType, obj.calYear).success(function (response) {
		                $scope.displayLeave(response.entity);
		            });	
	        	//}
	        }
	        //set checkLeaves type for toggle handling
	        obj.checkLeaves = val;
	        //set leaveyear to not load carryleaves and my leaves for each toggle within the same year
	        if($scope.leaveYear !== obj.calYear){
	        	$scope.leaveYear = obj.calYear;
	        	$scope.getLeaveBalance();
	        } else {
	        	$scope.setLeaveBalance();
	        }

	        //load holidays list by year only in month view
	        if(obj.calendarView != 'year'){
		        $scope.loadHolidays();	
	        }
	        
	    };
	    
	    $scope.setLeaveBalance = function(){
	    	var month = moment(obj.calMonth+1, 'MM').format('MMM').toLowerCase();
    		$scope.leaveBalance = $scope.empLeaveBalance[month]/8;
	    };
	    
	    $scope.getLeaveBalance = function(){
	    	leaveAPIservice.getEmpLeaveBalance(obj.calYear).success(function (response) {
	    		var month = moment(obj.calMonth+1, 'MM').format('MMM').toLowerCase();
	    		$scope.empLeaveBalance = response.entity;
	    		$scope.setLeaveBalance();
	    	});
	    };
	    $scope.loadHolidays = function(){
	    	//load holidays list by year
        	holidayAPIservice.getHolidaysByYear(obj.calYear).success(function (response) {
        		$.each(response.entity, function(i, val){
        			$scope.scope.events.push({
          			  title: val.holidayName,
          			  cssClass: 'holiday-calendar',
          		      startsAt: new Date(val.holidayDate),
          		      endsAt: new Date(val.holidayDate)
      		      	});
        		});
            });
	    };
	    
	    $scope.openModal = function(){
	        $('#leaveModal').modal();
	        $scope.setDefaults();
	    };
	    
	    $scope.onDataChange = function(){
	    	$scope.setDefaults($scope.data);
	    };
	    
	    $scope.setDefaults = function(data){
	    	$scope.data.notificationToList = [];
	    	$('#notify ul.dropdown-menu li.disabled').removeClass('disabled');
	    	var employee, employeeId, pk;
	    	if(data != undefined){
	    		employeeId = data.employeeId;
	    		pk = data.pk;
	    	}
	    	if(employeeId){
	    		employee = $scope.employees[getIndex(employeeId)];
	    	} else {
	    		employee = $scope.employees[getIndex(user.loggedUser.pk)];
	    	}
    		$scope.data.notificationToList.push(employee);
    		if(employee){
    			if(employee.pk != employee.supervisor){
        			var index = getIndex(employee.supervisor);
        			if(index > 0){
        				$scope.data.notificationToList.push($scope.employees[index]);	
                		$('#notify ul.dropdown-menu li[empId="'+$scope.employees[index].employeeId+'"]').addClass('disabled');	
        			}
        		}
        		//don't reset on change action in update
        		if(!pk)
        			isReqTypeDisabled(employee.pk);
        		var title = $scope.data.requestType.replace(/_/g, ' ');
        		if($scope.leaveType === 'PTO'){
        			//condition applied for LOP
        			title = ptoLeaveType[$scope.data.requestType].val;
        		}
        		$scope.data.title = employee.lastName+", "+employee.firstName+" - "+title;
    		}
	    };
	
	    $scope.applyLeave = function(){
	        $scope.data = {};
	        $scope.data.employeeId = user.loggedUser.pk;
	        $scope.data.requestType = $scope.leaveType;
	        $scope.data.dtEndHalf = "SECOND";
	        $scope.data.dtFromHalf = "FIRST";
	        $scope.data.startsAt = moment.now();
	        $scope.data.endsAt = moment.now();
	        $scope.onDtChange();
	        $scope.openModal();
	    };
	
	    getIndex = function(pk){
	    	if(bowser.name === 'Chrome'){
	    		var index = $scope.employees.findIndex(function(item, i){
	  	      	  return item.pk === pk;
	  	      	});
	    		return index;
	    	} else {// if(bowser.name === 'Internet Explorer'){
	    		var index = -1;
		    	$scope.employees.some(function(el, i) {
		    	    if (el.pk === pk) {
		    	        index = i;
		    	        return true;
		    	    }
		    	});
	    		return index;
	    	}
	    };
	    
	    var currentUser = function(){
	    	var deferred = $q.defer();
	    	currentUsr = profileAPIservice.getProfileDetails().success(function(response){
	    		$scope.displayLeaveBalance = true;
	    		if(response.entity.designations.designation == 'Subcontractor-Fixed'
	    			||response.entity.designations.designation == 'INTERN ADMIN'
	    				||response.entity.designations.designation == 'INTERN CONSULTING')
	    			$scope.displayLeaveBalance = false;
	    		user.loggedUser = response.entity;
	    		$scope.loggedUser = user.loggedUser.pk;
	    		deferred.resolve();
	    	});
	    	return deferred.promise;
	    }
	    
	    var loadAllEmployeeByNames = function(){
	    	var deferred = $q.defer();
	    	employeeAPIservice.loadAllEmployeeByNames().success(function(response) {
		        $scope.employeesList = response.entity;
		        $scope.employees = response.entity;
		    	deferred.resolve();
		    });
	    	return deferred.promise;
	    }
	    
	    var loadRoles = function(){
	    	var deferred = $q.defer();
	    	emprolesAPIservice.loadEmpRoles().success(function (response) {
		    	roles = response.entity;
		    	deferred.resolve();
		    });
	    	return deferred.promise;
	    }

	    $q.all([currentUser(),loadAllEmployeeByNames(), loadRoles()]).then(function(){
	    	 $scope.toggleLeave(obj.checkLeaves, new Date().getMonth());
		     $scope.getLeaveBalance();
	    });
	    
	    $scope.validate = function(){
	    	var errorMsg = '<p class="text-danger"></p>';
	    	var dtRangeStart = startVal;
	    	dtRangeStart.set('date', 1);
	    	dtRangeStart.set('hour', 0);
	    	var dtRangeEnd = endVal;
	    	var lastDay = new Date(dtRangeEnd.year(), dtRangeEnd.month() + 1, 0);
	    	dtRangeEnd.set('date', lastDay.getDate());
	    	dtRangeEnd.set('hour', 23);
	    	
			if($.trim($('#startDt').val()).length != 0 && $.trim($('#endDt').val()).length != 0
					&& new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY hh:mm a")).getFullYear() != new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY hh:mm a")).getFullYear()){
				$('#startDt').parent().addClass('has-error');
				$(errorMsg).html('Leaves across years is not allowed.  Please try to apply within same year.').insertAfter($('#startDt'));
			} else if(new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY hh:mm a")).getTime() >= new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY hh:mm a")).getTime()){
	    		$('#endDt').parent().addClass('has-error');
	        	$(errorMsg).html('End Date must be Greater than Start Date.').insertAfter($('#endDt'));
	    	} else if(new Date($('#startDt').val()).getTime() < new Date(dtRangeStart).getTime() 
	    			|| new Date($('#startDt').val()).getTime() > new Date(dtRangeEnd).getTime()){
	    		$('#startDt').parent().addClass('has-error');
	    		$(errorMsg).html('Start Date not allowed.').insertAfter($('#startDt'));
	    	} else if(new Date($('#endDt').val()).getTime() > new Date(dtRangeEnd).getTime() 
	    			|| new Date($('#endDt').val()).getTime() < new Date(dtRangeStart).getTime()){
	    		$('#endDt').parent().addClass('has-error');
	    		$(errorMsg).html('End Date not allowed.').insertAfter($('#endDt'));
	    	} else if($scope.data.notificationToList.length > 10){
	    		$('#notifyList').parent().addClass('has-error');
	    		$(errorMsg).html('Notify users must be less than or equal to 10 selections.').insertAfter($('#notifyList'));
	    	}
    	};
	    
	    function round(value) {
	        return Math.round(value * 2) / 2;
	    }
	
	    $scope.displayLeave = function(data){
	        //$scope.scope.events.splice(0, $scope.scope.events.length);
	        $.each(data, function(i, data){
	        	 var val = data[0];
	             val.startsAt = new Date(val.startsAt);
	             val.endsAt = new Date(val.endsAt);
	             val.type = $scope.displayType;
	             //val.hours = data[2];
	             val.status = data[4];
	             if(val.status === 'PENDING')
	            	 val.cssClass = 'pending-leaves';
	             else if(val.status === 'APPROVED')
	            	 val.cssClass = 'approved-leaves';
	             else if(val.status === 'REJECTED')
	            	 val.cssClass = 'rejected-leaves';
	             var notifyListPk = [];
	             val.notificationToList = data[3].split(",");
	             if(val.notificationToList){
	                 $.each(val.notificationToList, function(i, item){
	                     notifyListPk.push(parseInt(item));
	                 });
	                 val.notificationToList.splice(0, val.notificationToList.length);
	                 $.each(notifyListPk, function(i, item){
	                     val.notificationToList.push($scope.employees[getIndex(item)]);
	                 });
	             }
//	             $scope.scope.events[val.pk] = val;
	             $scope.scope.events.push(val);
	             eventArr[val.pk] = i;
	        });
	    };
	
	    $scope.saveLeave = function(){
	        leaveAPIservice.applyLeave($scope.data).success(function (response) {
	            response.entity.startsAt = new Date(response.entity.startsAt);
	            response.entity.endsAt = new Date(response.entity.endsAt);
	            $scope.scope.events.push(response.entity);
	            if($scope.title == 'Leave')
	            	perfUtils.getInstance().successMsg("Leave Applied Successfully!");
	            else
	            	perfUtils.getInstance().successMsg("WFH Applied Successfully!");
	            $('#leaveModal').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	
	    $scope.updateLeave = function(){
	        leaveAPIservice.updateLeave($scope.data).success(function (response) {
	           $.grep($scope.scope.events, function (element, index) {
	                if($scope.data.pk === element.pk){
	                    $scope.scope.events[index] = $scope.data;
	                }
	                return element.pk === $scope.data.pk;
	            });
	            $scope.data.hours = response.entity.hours;
	            $scope.data.cssClass = 'pending-leaves';
	            //check for WFH types as GM will be added by default in case of no of wfh gets exceeding its limit
	            if($scope.leaveType === 'WFH' 
	            	&& response.entity.notificationToList.length != $scope.data.notificationToList.length){
	            	var notifyList = response.entity.notificationToList;
	            	$scope.data.notificationToList = [];
	            	$.each(notifyList, function(i, item){
	            		$scope.data.notificationToList.push(item);
	                });
	            }
	            $('div[leavepk="'+$scope.data.pk+'"').removeClass('approved-leaves').removeClass('rejected-leaves').addClass('pending-leaves');
	            $scope.dtInstance.dataTable.fnUpdate($scope.data, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
	            perfUtils.getInstance().successMsg($scope.title+" Updated Successfully!");
	            $('#leaveModal').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	    
	    $scope.deleteModal = function(){
	    	$('#deleteLeave').modal();
	    };
	
	    $scope.deleteLeave = function(){
	        leaveAPIservice.deleteLeave($scope.data).success(function () {
	        	var idx;
	            $.grep($scope.scope.events, function (element, index) {
	                if($scope.data.pk === element.pk){
	                	idx = index;
	                }
	                return element.pk === $scope.data.pk;
	            });
	            $scope.scope.events.splice(idx, 1);
	            $scope.dtInstance.dataTable.fnDeleteRow($scope.dtInstance.DataTable.$('tr.selected'));
	            perfUtils.getInstance().successMsg($scope.title+" Deleted Successfully!");
	            $('#leaveModal').modal('hide');
	            $('#deleteLeave').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	
	    $scope.onDtChange = function(){
	    	if(new Date($scope.data.startsAt).getTime() > new Date($scope.data.endsAt).getTime()){
	        	$scope.data.endsAt = $scope.data.startsAt;
	        }
	        var time9 = 60*1000*60*9;
	        var time1_30 = 60*1000*60*13.5;
	        var time5_30 = 60*1000*60*17.5;
	        if($scope.data.startsAt && $scope.data.dtFromHalf) {
	            var stDt = new Date($scope.data.startsAt).toDateString();
	            if($scope.data.dtFromHalf === 'FIRST'){
	                stDt = new Date(stDt).getTime() + (time9);
	            } else {
	                stDt = new Date(stDt).getTime() + (time1_30);
	            }
	            $scope.data.startsAt = new Date(stDt);
	        }
	        if($scope.data.endsAt && $scope.data.dtEndHalf){
	            var endDt = new Date($scope.data.endsAt).toDateString();
	            if($scope.data.dtEndHalf === 'FIRST'){
	                endDt = new Date(endDt).getTime() + (time1_30);
	            } else {
	                endDt = new Date(endDt).getTime() + (time5_30);
	            }
	            $scope.data.endsAt = new Date(endDt);
	        }
	    };
	    
	    $scope.dtColumns = [
	        DTColumnBuilder.newColumn('title').withTitle('Title'),
	        DTColumnBuilder.newColumn('employeeNamesView').withTitle('Employee Name').renderWith(function(data, type, full) {
	            return full.employeeNamesView.lastName+', '+full.employeeNamesView.firstName;
	        }),
	        DTColumnBuilder.newColumn('startsAt').withTitle('Starts').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY hh:mm A");
	        }),
	        DTColumnBuilder.newColumn('endsAt').withTitle('Ends').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY hh:mm A");
	        }),
	        DTColumnBuilder.newColumn('hours').withTitle('Days').renderWith(function(data) {
	            return (data/8);
	        })
	     ];
	     var paramObj = {
	         "vm" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         'editFormId' : 'leaveModal',
	         "deleteFormId": 'deleteLeave'
	         //"deleteRow" : false
	     };
	     perfDatatable.loadTable.init(paramObj);
	};
	LeaveController.$inject = ['$scope', '$rootScope', 'moment', '$filter', 'user', 'leaveAPIservice', 'employeeAPIservice', 'calendarConfig', 
	                           'DTColumnBuilder' , '$compile', 'DTOptionsBuilder', 'profileAPIservice', '$q',
	                           'holidayAPIservice', '$timeout', 'emprolesAPIservice'];
	mainApp.controller('leaveController', LeaveController);
})(angular);