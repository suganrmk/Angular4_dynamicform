/* Profile controller */
mainApp.controller('profileController', ['$scope', '$controller', 'profileAPIservice', 'employeeAPIservice', 'emprolesAPIservice', 
                                      'projectMemberAPIservice', 'user', 
                   function($scope, $controller, profileAPIservice, employeeAPIservice, emprolesAPIservice, projectMemberAPIservice, user) {
     $scope.data = null;

     $scope.isProfile = true;     
     employeeAPIservice.loadEmployees().success(function (response) {
        $scope.employees = response.entity;
        profileAPIservice.getProfileDetails().success(function (profileResponse) {
            $scope.data = profileResponse.entity;
            //loadSkills($scope.data.employeeId);
        });
     });

     emprolesAPIservice.loadRolesByEmpId().success(function (response) {
    	$scope.empRoles = response.entity;
     });
     
     projectMemberAPIservice.loadMyProjects().success(function (response) {
    	$scope.projects = response.entity;
     });
     
     $scope.updPwd = function(){
    	$('input[type="password"]').val('');
    	$('#updatePassword').modal();
     };
     
     $scope.updatePwd = function(){
    	var errorMsg = '<p class="text-danger"></p>';
    	var reg = /^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*_=+-/()-? "]).*$/;
    	if($scope.currentPassword == $scope.newPassword){
    		$('#newPassword').parent().addClass('has-error');
			$(errorMsg).html('Old Password and New Password are same.').insertAfter($('#newPassword'));
			return false;
     	} else if($scope.newPassword != $scope.confirmPassword){
    		$('#newPassword').parent().addClass('has-error');
			$(errorMsg).html('New Password and Confirm Password are not equal.').insertAfter($('#newPassword'));
			return false;
     	} else if(!reg.test($scope.confirmPassword)){
     		$('#newPassword').parent().addClass('has-error');
			$(errorMsg).html('New Password must contain atleast 1 special character and one number.').insertAfter($('#newPassword'));
			return false;
     	}
    	var passphrase = Math.random().toString(36).substring(1);
	    var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
	    var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
	    var aesUtil = new AesUtil(128, 1000);
	    var ciphertext = aesUtil.encrypt(salt, iv, passphrase, $scope.currentPassword);
    	profileAPIservice.updPwd(passphrase, iv, salt, ciphertext, $scope.newPassword).success(function (response) {
    		perfUtils.getInstance().successMsg("Password Updated Successfully!");
    		$('#updatePassword').modal('hide');
        });
     };
     
     $scope.submit = function() {
       if ($scope.data) {
          employeeAPIservice.updateEmployee($scope.data).success(function () {
             perfUtils.getInstance().successMsg('Profile updated Successfully!');
          });
       }
     };
     angular.extend(this, $controller('empProfileController', {
         $scope: $scope
     }));
}]);

mainApp.controller('empProfileController', ['$scope', 'designationAPIservice', function($scope, designationAPIservice) {
    $scope.dob = {
        opened: false
    };

    $scope.dobDate = function() {
        $scope.dob.opened = true;
    };

    $scope.joinDate = {
        opened: false
    };

    $scope.joinDate = function() {
        $scope.joinDate.opened = true;
    };
    
    $scope.jobEffDate = {
        opened: false
    };

    $scope.jobEffDate = function() {
        $scope.jobEffDate.opened = true;
    };
    
    $scope.lastWorkDate = {
        opened: false
    };

    $scope.lastWorkDate = function() {
        $scope.lastWorkDate.opened = true;
    };

    $scope.validate = function(){
		perfUtils.getInstance().validateFutureDate(['birthDate']);
		if($scope.data.designation_effective_date != null){
			if(new Date(moment.utc($('#jobEffDate').val(), "DD-MMM-YYYY")).getTime() < new Date(moment.utc($('#joinDate').val(), "DD-MMM-YYYY")).getTime()){
	    		var errorMsg = '<p class="text-danger"></p>';
	    		$('#jobEffDate').parent().addClass('has-error');
	        	$(errorMsg).html('Designation Effective Date must be Greater than or equal to JoinDate.').insertAfter($('#jobEffDate'));
	    	}
		}
		if($scope.data.lastWorkDate != null){
			if(new Date(moment.utc($('#lastWorkDate').val(), "DD-MMM-YYYY")).getTime() < new Date(moment.utc($('#joinDate').val(), "DD-MMM-YYYY")).getTime()){
	    		var errorMsg = '<p class="text-danger"></p>';
	    		$('#lastWorkDate').parent().addClass('has-error');
	        	$(errorMsg).html('Last Working Date must be Greater than or equal to JoinDate.').insertAfter($('#lastWorkDate'));
	    	}
		}
	};
    
    designationAPIservice.getDesignationDetails().success(function (response) {
        $scope.designations = response.entity;
    });
}]);