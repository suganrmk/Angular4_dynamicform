mainApp.factory('leaveAPIservice',['$http', function($http) {
    var leaveAPI = {};
    leaveAPI.loadLeavesByYear = function(leaveType, calYear) {
        return $http({
          method: 'get',
          url: perfUrl['loadLeavesByYear'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
        });
    };
    leaveAPI.loadLeavesByMonth = function(leaveType, calYear, calMonth) {
        return $http({
          method: 'get',
          url: perfUrl['loadLeavesByMonth'].replace('{leaveType}', leaveType).replace('{calYear}', calYear).replace('{calMonth}', calMonth)
        });
    };
    leaveAPI.applyLeave = function(data) {
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['applyLeave']
        });
    };
    leaveAPI.updateLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['updateLeave']
        });
    };
    leaveAPI.deleteLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['deleteLeave']
        });
    };
    leaveAPI.loadById = function(id){
      return $http({
        method: 'get',
        url: perfUrl['loadLeaveById']+id
      });
    };
    leaveAPI.loadMyLeaves = function(leaveType, calYear){
      return $http({
        method: 'get',
        url: perfUrl['loadMyLeaves'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
      });
    };
    leaveAPI.getLeaveBalance = function(leaveType, calYear, calMonth){
        return $http({
          method: 'get',
          url: perfUrl['getLeaveBalance'].replace('{leaveType}', leaveType).replace('{calYear}', calYear).replace('{calMonth}', calMonth)
        });
    };
    leaveAPI.getEmpLeaveBalance = function(calYear, empId){
    	var url = perfUrl['getEmpLeaveBalance'].replace('{calYear}', calYear);
    	if(empId)
    		url += '?empId='+empId
        return $http({
          method: 'get',
          url: url
        });
    };
    leaveAPI.getLeaveBalanceByMonth = function(leaveType, calYear, empId){
    	if(empId === undefined)
    		empId = '';
        return $http({
          method: 'get',
          url: perfUrl['getLeaveBalanceByMonth'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)+'?empId='+empId
        });
    };
    leaveAPI.getLeaveBalanceByType = function(leaveType, calYear, empId){
    	if(empId === undefined)
    		empId = '';
        return $http({
          method: 'get',
          url: perfUrl['getLeaveBalanceByType'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)+'?empId='+empId
        });
    };
    leaveAPI.loadLeaveReport = function(data){
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['loadLeaveReport']
        });
    };
    leaveAPI.loadAllLeaveReport = function(data){
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['loadAllLeaveReport']
        });
    };
    leaveAPI.getCarryLeaves = function(year, empId){
    	if(empId === undefined)
    		empId = '';
    	return $http({
            method: 'get',
            url: perfUrl['getCarryLeaves'].replace('{year}', year)+'?empId='+empId
        });
    };
    return leaveAPI;
}]);