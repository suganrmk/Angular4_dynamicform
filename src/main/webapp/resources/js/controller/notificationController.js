(function(angular) {
	/* Notification Controller*/
	var ptoObj, wfhObj, msgObj;
	var NotificationController = function($scope, $controller, DTColumnBuilder, roles, notificationAPIservice, employeeAPIservice){
		var _this = this;
		var ptoList, wfhList, msgList;
		
		$scope.ptoSelectList = [];
		$scope.wfhSelectList = [];
		
		var pto_wfh_SelList = [{'key':'PENDING', 'val':'PENDING FOR APPROVAL'},{'key':'REJECTED', 'val':'REJECTED'},{'key':'SUMMARY', 'val':'SUMMARY'}];
		
		$scope.ptoSelectList = pto_wfh_SelList;
		$scope.wfhSelectList = pto_wfh_SelList;
		
		$scope.ptoSelect = pto_wfh_SelList[0];
		$scope.wfhSelect = pto_wfh_SelList[0];
		
		$scope.loadPTONotifications = function(){
			notificationAPIservice.loadNotificationByType('PTO', $scope.ptoSelect.key).success(function(response) {
				ptoList = response.entity;
				ptoObj.dtInstance.DataTable.clear().draw();
				ptoObj.dtInstance.DataTable.rows.add(response.entity).draw();
	        });	
		}
		$scope.loadPTONotifications();
		$scope.loadWFHNotifications = function(){
			notificationAPIservice.loadNotificationByType('WFH', $scope.wfhSelect.key).success(function(response) {
				wfhList = response.entity;
				wfhObj.dtInstance.DataTable.clear().draw();
				wfhObj.dtInstance.DataTable.rows.add(response.entity).draw();
	        });	
		}
		$scope.loadWFHNotifications();
		/*notificationAPIservice.loadNotificationMsgs().success(function(response) {
			msgList = response.entity;
			msgObj.dtInstance.DataTable.clear().draw();
			msgObj.dtInstance.DataTable.rows.add(response.entity).draw();
        });*/
		$scope.leaveView = function(type, val){
			var data;
			if(type === 'PTO'){
				data = ptoList[getIndex(ptoList, val)];	
			} else {
				data = wfhList[getIndex(wfhList, val)];
			}
			$scope.leaveData = data[0];
			var notify = data[1];
			$scope.leaveData.notifyPk = notify.pk;
			$scope.leaveData.notifyComments = notify.comments;
			$scope.leaveData.notificationType = notify.notificationType;
			$scope.isMarkRead = false;
			$scope.isApproveReject = false;
			if(notify.notificationStatus === 'REJECTED' 
					&& notify.flag === 0){
				$scope.isMarkRead = true;
			} else if(notify.notificationStatus === 'PENDING' && notify.flag === 0){
				$scope.isApproveReject = true;
			}
			$('#notificationModal').modal();
		};
		
		$scope.reloadData = function(type){
			if($scope.leaveData.notificationType === 'PTO'){
				$scope.loadPTONotifications();
			} else
				$scope.loadWFHNotifications();
			
			$scope.loadNotificationCount();
		};
		
		$scope.approveNotification = function(){
			$scope.approveRejectNotification('APPROVED');
		};
		
		$scope.rejectNotification = function(){
			$scope.approveRejectNotification('REJECTED');
		};
		
		$scope.delegateNotification = function(){
			employeeAPIservice.loadAllEmployeeByNames().success(function(response) {
		        $scope.employees = response.entity;
		        $('#delegateModal').modal();
		    });
		};
		
		$scope.delegate = function(){
			notificationAPIservice.delegateNotification($scope.leaveData.notifyPk, $scope.leaveData.delegateEmpId).success(function(response){
				$('#delegateModal').modal('hide');
				$('#notificationModal').modal('hide');
				perfUtils.getInstance().successMsg($scope.leaveData.notificationType+' Delegated Successfully!');
				$scope.reloadData($scope.leaveData.notificationType);
			});
		};
		
		$scope.approveRejectNotification = function(status){
			notificationAPIservice.approveRejectNotification($scope.leaveData.notifyPk, status, $scope.leaveData.notifyComments).success(function(response){
				$('#notificationModal').modal('hide');
				perfUtils.getInstance().successMsg($scope.leaveData.notificationType+' '+status+' Successfully!');
				$scope.reloadData($scope.leaveData.notificationType);
			});
		};
		
		$scope.markAsRead = function(){
			notificationAPIservice.markReadNotification($scope.leaveData.notifyPk).success(function(response){
				perfUtils.getInstance().successMsg($scope.leaveData.notificationType+' Updated Successfully!');
				$scope.reloadData($scope.leaveData.notificationType);
			});
		};
		
		getIndex = function(list, pk){
	    	if(bowser.name === 'Chrome'){
	    		var index = list.findIndex(function(item, i){
	  	      	  return item[0].pk == pk;
	  	      	});
	    		return index;
	    	} else {// if(bowser.name === 'Internet Explorer'){
	    		var index = -1;
	    		list.some(function(el, i) {
		    	    if (el[0].pk == pk) {
		    	        index = i;
		    	        return true;
		    	    }
		    	});
	    		return index;
	    	}
	    };
	    
	    angular.extend(this, $controller('notificationCountController', {
	        $scope: $scope
	    }));
	};
	
	function ptoNotificationTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, notificationAPIservice) {
		ptoObj = this;
		ptoObj.title = 'PTO Notifications';
		ptoObj.type= 'PTO';
		setLeaveTable(ptoObj, $scope, $compile, DTOptionsBuilder, DTColumnBuilder, notificationAPIservice);
	}
	ptoNotificationTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'notificationAPIservice'];
	mainApp.controller('ptoNotificationTable', ptoNotificationTable);
	
	function wfhNotificationTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, notificationAPIservice) {
		wfhObj = this;
		wfhObj.title = 'WFH Notifications';
		wfhObj.type= 'WFH';
		setLeaveTable(wfhObj, $scope, $compile, DTOptionsBuilder, DTColumnBuilder, notificationAPIservice);
	}
	wfhNotificationTable.$inject = ['$scope', '$compile', 'DTOptionsBuilder','DTColumnBuilder', 'notificationAPIservice'];
	mainApp.controller('wfhNotificationTable', wfhNotificationTable);
	
	var setLeaveTable = function(leaveObj, $scope, $compile, DTOptionsBuilder, DTColumnBuilder, notificationAPIservice){
		leaveObj.dtColumns = [
	        DTColumnBuilder.newColumn('title').withTitle('Title').renderWith(function(data, type, full) {
	            return full[0].title;
	        }),
	        DTColumnBuilder.newColumn('startsAt').withTitle('Date').renderWith(function(data, type, full) {
	        	return moment(full[0].startsAt).format("DD-MMM-YY hh:mm")+" - "+moment(full[0].endsAt).format("DD-MMM-YY hh:mm");
	        }),
	        DTColumnBuilder.newColumn('startsAt').withTitle('Status').withOption('width', '50px').renderWith(function(data, type, full) {
	        	return full[1].notificationStatus;
	        }),
	        DTColumnBuilder.newColumn('comments').withTitle('#').withOption('width', '20px').renderWith(function(data, type, full) {
	        	var btnClass = 'btn-warning';
	        	if(full[1].flag === 1){
	        		btnClass = 'btn-default';
	        	}
	        	return '<div style="display:inline-flex;"><button class="btn '+btnClass+'" title="View Details" ng-click=leaveView("'+leaveObj.type+'","'+full[0].pk+'")><i class="fa fa-info-circle"></i></button>';
	        })
	     ];
	     var paramObj = {
	         "vm" : leaveObj,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : notificationAPIservice,
	         'editFormId' : 'notificationModal',
	         'actions': false
	     };
	     perfDatatable.loadTable.init(paramObj);
	};
	
	NotificationController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'roles', 'notificationAPIservice', 'employeeAPIservice'];
	mainApp.controller('notificationController', NotificationController);
	
})(angular);