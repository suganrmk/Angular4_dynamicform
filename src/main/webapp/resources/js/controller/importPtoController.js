(function(angular) {
	/* Import PTO Controller*/
	
	var ImportPtoController = function($scope, $controller, ptoAPIService,
       				DTColumnBuilder , $compile, DTOptionsBuilder){
	    $scope.uploadFile = function(){
	    	var file = $scope.uploadPto;
	        ptoAPIService.uploadFileToUrl(file).success(function(data){
	            $('input[type="file"]').val('');
	            if(data)
	            	perfUtils.getInstance().successMsg('PTO document processed successfully!');
	            else
	                $scope.msg="An error occurred during document processing!";
	        });
	    };
	    
	    $scope.uploadEbsFile = function(){
	    	var file = $scope.uploadEbs;
	    	ptoAPIService.importEbsPto(file).success(function(data){
	            $('input[type="file"]').val('');
	            if(data)
	            	perfUtils.getInstance().successMsg('EBS document processed successfully!');
	            else
	                $scope.msg="An error occurred during document processing!";
	        });
	    };
	    
	    $scope.uploadLeaveBalance = function(){
	    	var file = $scope.uploadLeaveBal;
	        ptoAPIService.uploadLeaveBal(file).success(function(data){
	            $('input[type="file"]').val('');
	            if(data)
	            	perfUtils.getInstance().successMsg('Opening Balance processed successfully!');
	            else
	                $scope.msg="An error occurred during document processing!";
	        });
	    };
	    
	    $scope.uploadWfh = function(){
	    	var file = $scope.uploadWfhData;
	        ptoAPIService.importWfh(file).success(function(data){
	            $('input[type="file"]').val('');
	            if(data)
	            	perfUtils.getInstance().successMsg('WFH document processed successfully!');
	            else
	                $scope.msg="An error occurred during document processing!";
	        });
	    };
	    
	    $scope.uploadEntry = function(){
	    	var file = $scope.uploadEntryData;
	        ptoAPIService.importEntry(file).success(function(data){
	            $('input[type="file"]').val('');
	            if(data)
	            	perfUtils.getInstance().successMsg('Office Entry document processed successfully!');
	            else
	                $scope.msg="An error occurred during document processing!";
	        });
	    };
	};
	
	ImportPtoController.$inject = ['$scope','$controller', 'ptoAPIService', 
	                       		'DTColumnBuilder' , '$compile', 'DTOptionsBuilder'];
	mainApp.controller('importPtoController', ImportPtoController);
})(angular);	