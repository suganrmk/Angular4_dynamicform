mainApp.factory('notificationAPIservice', ['$http', function($http) {
    var notificationAPI = {};
    notificationAPI.loadNotificationCount = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadNotificationCount']
        });
    };
    notificationAPI.loadNotificationByType = function(type, status) {
        return $http({
          method: 'get',
          url: perfUrl['loadNotificationByType'].replace('{type}', type).replace('{status}', status)
        });
    };
    notificationAPI.loadNotificationMsgs = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadNotificationMsgs']
        });
    };
    notificationAPI.approveRejectNotification = function(id, status, notifyComments) {
        return $http({
          method: 'put',
          data: notifyComments,
          url: perfUrl['approveRejectNotification'].replace('{id}', id).replace('{status}', status)
        });
    };
    notificationAPI.markReadNotification = function(id) {
        return $http({
          method: 'get',
          url: perfUrl['markReadNotification'].replace('{id}', id)
        });
    };
    notificationAPI.delegateNotification = function(id, empId) {
        return $http({
          method: 'put',
          url: perfUrl['delegateNotification'].replace('{id}', id).replace('{empId}', empId)
        });
    };
    return notificationAPI;
}]);