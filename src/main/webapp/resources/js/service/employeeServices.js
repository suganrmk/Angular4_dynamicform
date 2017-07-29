mainApp.factory('employeeAPIservice',['$http', function($http) {
    var employeeAPI = {};
    employeeAPI.loadById = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeById']+empId
        });
    };
    employeeAPI.loadEmployee = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployee']
        });
    };
    employeeAPI.loadUserNameViewById = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['loadUserNameViewById']+empId
        });
    };
    employeeAPI.loadByEmployeeById = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['loadByEmployeeById']+empId
        });
    };
    employeeAPI.loadAllEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllEmployees']
        });
    };
    employeeAPI.loadEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployees']
        });
    };
    employeeAPI.loadAllEmployeeByNames = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllEmployeeByNames']
        });
    };
    employeeAPI.loadAllEmployeeNamesByDate = function(fromDate, toDate) {
        return $http({
          method: 'get',
          url: perfUrl['loadAllEmployeeNamesByDate'].replace('{fromDate}', fromDate).replace('{toDate}', toDate)
        });
    };
    employeeAPI.loadEmployeesBySupervisor = function(limit, empId, year) {
    	if(empId === undefined)
    		empId = '';
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeesBySupervisor'].replace('{limit}', limit).replace('{year}', year)+'?empId='+empId
        });
    };
    employeeAPI.addEmployee = function(employee) {
        return $http({
          method: 'post',
          data : employee,
          url: perfUrl['addEmployee']
        });
    };
    employeeAPI.updateEmployee = function(employee) {
        return $http({
          method: 'put',
          data : employee,
          url: perfUrl['updateEmployee']
        });
    };
    employeeAPI.generateCrendentials = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['generateCrendentials']+empId
        });
    };
    employeeAPI.resetPassword = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['resetPassword']+empId
        });
    };
    employeeAPI.loadEmployeeByDesHistory = function(fromDate, toDate, designation) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeByDesHistory'].replace('{fromDate}', fromDate).replace('{toDate}', toDate).replace('{designation}', designation)
        });
    };
    return employeeAPI;
}]);