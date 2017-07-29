mainApp.factory('ebsReportAPIservice',['$http', function($http) {
	var ebsReportAPI = {};
	ebsReportAPI.loadEbsLeaveReport = function(data){
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['loadEbsLeaveReport']
        });
    };
    return ebsReportAPI;
}]);