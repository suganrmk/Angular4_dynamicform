(function(angular) {
	/* Home Controller*/
	var HomeController = function($scope, $controller, currentUser, roles){
		$scope.currentUser = currentUser.data.entity.employeeId;
		var sessionId = $('input[name="sessId"]').val();
		var role = roles.data.entity;
		$scope.role = role;
		var jsessionId = window.localStorage.getItem("JSESSIONID");
		//check the device
	    if(bowser.mobile || bowser.tablet){
	    	$scope.isMobile = true;
	    } else {
	    	$scope.tdcUrl = tdcUrl+'?jsession='+jsessionId;
	    }
	    $scope.ctaUrl = ctaUrl+'?jsession='+jsessionId;
	    
	    //validate session for a periodic interval
	    //Use $.ajax function instead of $http since it's configured for 
	    //angular requests(loading symbol will pop-up on every request and error handler is different as it will prompt user)
        /*setInterval(function(){ $scope.validateSession(); }, 10000);
	    $scope.validateSession = function(){
			$.ajax({
				type : "get",
				url : context + 'session/validateSession',
				contentType : "application/json",
				dataType : "json",
				error : function(xhr, ajaxOptions, thrownError) {
					if(bowser.mobile || bowser.tablet){
		    			window.location.href=mobileLoginPage;
		    	    } else {
		    	    	window.location.href=deskLoginPage;
		    	    }
				}
			});
	    };*/
	};
	
	HomeController.$inject = ['$scope','$controller', 'currentUser', 'roles'];
	mainApp.controller('homeController', HomeController);
})(angular);