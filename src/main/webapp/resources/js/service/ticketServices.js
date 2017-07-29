mainApp.factory('ticketService',['$http', function ($http) {
    var ticketPtoAPI = {};
    ticketPtoAPI.saveTicket = function(file, data) {
         var fd = new FormData();
         fd.append('uploadTicketDoc', file);
         fd.append('ticket', angular.toJson(data));
         return $http({
             method: 'post',
             data : fd,
             url: perfUrl['addTicket'],
             transformRequest: angular.identity,
             headers: {'Content-Type': undefined}
         });
    };
    ticketPtoAPI.updateTicket = function(file, data) {
        var fd = new FormData();
        fd.append('uploadTicketDoc', file);
        fd.append('ticket', angular.toJson(data));
        return $http({
            method: 'post',
            data : fd,
            url: perfUrl['updateTicket'],
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
   };
   ticketPtoAPI.loadTicketsById = function(id) {
       return $http({
         method: 'get',
         url: perfUrl['loadTicketsById']+'?id='+id
       });
   };
   ticketPtoAPI.loadTicketByTicketId = function(id) {
       return $http({
         method: 'get',
         url: perfUrl['loadTicketByTicketId']+'?id='+id
       });
   };
   ticketPtoAPI.deleteComment = function(id) {
       return $http({
         method: 'put',
         url: perfUrl['deleteComment']+'?id='+id
       });
   };
   ticketPtoAPI.deleteAttachment = function(id) {
       return $http({
         method: 'put',
         url: perfUrl['deleteAttachment']+'?id='+id
       });
   };
   ticketPtoAPI.loadTicketsReport = function(data){
       return $http({
         method: 'post',
         data : data,
         url: perfUrl['loadTicketsReport']
       });
   };
   return ticketPtoAPI;
}]);