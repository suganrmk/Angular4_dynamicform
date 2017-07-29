mainApp.factory('emprolesAPIservice',['$http', function($http) {
    var emprolesAPI = {};
    emprolesAPI.loadEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmpByRoles']+roleId
        });
    };
    emprolesAPI.loadNonEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadNonEmpByRoles']+roleId
        });
    };
    emprolesAPI.loadEmpRoles = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmpRoles']
        });
    };
    emprolesAPI.saveEmpRoles = function(emproles) {
    	return $http({
            method: 'post',
            data : emproles,
            url: perfUrl['saveEmpRoles']
        });
    };
    emprolesAPI.loadComponents = function() {
    	return $http({
            method: 'get',
            url: 'js/constants/components.json'
        });
    };
    emprolesAPI.loadTaqComponents = function() {
    	return $http({
            method: 'get',
            url: 'js/constants/taqroles.json'
        });
    };
    emprolesAPI.loadRolesByEmpId = function() {
    	return $http({
            method: 'get',
            url: perfUrl['loadRolesByEmpId']
        });
    };
    return emprolesAPI;
}]);