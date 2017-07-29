mainApp.factory('ptoAPIService',['$http', function ($http) {
    var importPtoAPI = {};
    importPtoAPI.uploadFileToUrl = function(file) {
         var fd = new FormData();
         fd.append('uploadFiles',file);
         return $http({
             method: 'post',
             data : fd,
             url: perfUrl['importPto'],
             transformRequest: angular.identity,
             headers: {'Content-Type': undefined}
         });
    };
    importPtoAPI.importEbsPto = function(file) {
        var fd = new FormData();
        fd.append('uploadFiles',file);
        return $http({
            method: 'post',
            data : fd,
            url: perfUrl['importEbsPto'],
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
   };
   importPtoAPI.uploadLeaveBal = function(file) {
        var fd = new FormData();
        fd.append('uploadOpeningBalance',file);
        return $http({
            method: 'post',
            data : fd,
            url: perfUrl['uploadLeaveBal'],
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
   };
   importPtoAPI.importWfh = function(file) {
       var fd = new FormData();
       fd.append('importWfhCalInfoFromIcs',file);
       return $http({
           method: 'post',
           data : fd,
           url: perfUrl['importWfhCalInfoFromIcs'],
           transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
       });
   };
   importPtoAPI.importEntry = function(file) {
       var fd = new FormData();
       fd.append('importOfficeEntry',file);
       return $http({
           method: 'post',
           data : fd,
           url: perfUrl['importOfficeEntry'],
           transformRequest: angular.identity,
           headers: {'Content-Type': undefined}
       });
   };
   return importPtoAPI;
}]);