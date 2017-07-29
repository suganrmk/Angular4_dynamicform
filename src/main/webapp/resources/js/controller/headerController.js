(function(angular) {
	/*Header Controller*/
	var HeaderController = function($scope, $controller,  profileAPIservice, dashboardAPIservice, emprolesAPIservice, 
			notificationAPIservice, commonAPIservice, $q, $timeout){
		var _this = this;
		emprolesAPIservice.loadEmpRoles().success(function (response) {
	        $scope.role = response.entity;
	    });
		
		var profileDetails = function(){
			profileAPIservice.getProfileDetails().success(function (response) {
		        $scope.user = response.entity;
		        $('#navBarInfo').show();
		    });	
		};
		
	    dashboardAPIservice.getVersion().success(function (response) {
//	        $('#version').html('v'+response.entity.version);
	        if(bowser.mobile || bowser.tablet){
	        	if(mobileVersion < response.entity.minSupportedVersion){
	        		console.log('Unsupported Version');
	        		perfUtils.getInstance().redirectToStore('Your app version is not supported anymore. Please update to the latest version.','https://play.google.com/store/apps/details?id=com.perfhr.app').then(function(val){
	        			$('#logout').trigger('click');
	    			});
	        	} else if(mobileVersion < response.entity.version){
	        		perfUtils.getInstance().warningMsg('New Update is available!');
	        	}
    	    }
	    });
	    
	    //check the device
	    if(bowser.mobile || bowser.tablet){
	    	$scope.displayRefresh = true;
	    	$('body').removeClass('sidebar-collapse');
	    }

	    $scope.refreshPage = function(){
	    	location.reload();
	    }
	    
	    $scope.logout = function(){
	    	commonAPIservice.getRequest(perfUrl['logout']).success(function (response) {
	    		if(bowser.mobile || bowser.tablet){
	    			window.location.href=mobileLoginPage;
	    	    } else {
	    	    	window.location.href=deskLoginPage;
	    	    }
	    	});
	    }
	    
	    $scope.$on('handleNotificationCount', function() {
	    	notificationAPIservice.loadNotificationCount().success(function(response){
				$scope.notificationCount = 0;
				$scope.notifications = [];
				var count=0;
				$.each(response.entity, function(i, val){
					var notificationData = {};
					count = count+val[0];
					var type = val[1];
					var status = val[2];
					if(type === 'PTO'){
						notificationData.cls = 'text-maroon';
						notificationData.icon = 'fa-plane';
					} else if(type === 'WFH'){
						notificationData.cls = 'text-orange';
						notificationData.icon = 'fa-universal-access';
					}
					if(status == 'PENDING')
						notificationData.msg = 'needs Approval.';
					else if(status == 'APPROVED')
						notificationData.msg = 'Approved.';
					else if(status == 'REJECTED')
						notificationData.msg = 'Rejected.';
					notificationData.type=type;
					notificationData.count = val[0];
					$scope.notifications.push(notificationData);
				});
				if(count !== 0){
					$scope.notificationCount = count;
					$('.dropdown .notification-count').trigger('click');
					$timeout(function(){
						if($('.dropdown.notifications-menu').hasClass('open'))
							$('.dropdown .notification-count').trigger('click');
					}, 5000);
				} else {
					$scope.notificationCount = 0;
				}
			});
	    }); 
	    /**/
	    angular.extend(this, $controller('notificationCountController', {
	         $scope: $scope
	    }));
	    $scope.loadNotificationCount();
	    $q.all([profileDetails()]).then(function(){
	    	
	    });
	};
	
	HeaderController.$inject = ['$scope','$controller','profileAPIservice','dashboardAPIservice', 'emprolesAPIservice', 
	                            'notificationAPIservice', 'commonAPIservice', '$q', '$timeout'];
	mainApp.controller('headerController', HeaderController);
	
	var notificationCountController = function($scope, $rootScope, notificationAPIservice){
		var _this = this;
		$scope.loadNotificationCount = function(){
			$rootScope.$broadcast('handleNotificationCount');
		};
	};
	
	notificationCountController.$inject = ['$scope', '$rootScope', 'notificationAPIservice'];
	mainApp.controller('notificationCountController', notificationCountController);
})(angular);