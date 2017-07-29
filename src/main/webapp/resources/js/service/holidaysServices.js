mainApp.factory('holidayAPIservice', ['$http', function($http) {
    var holidayAPI = {};
    holidayAPI.getHolidaysByYear = function(year) {
        return $http({
          method: 'get',
          url: perfUrl['getHolidaysByYear']+year
        });
    };
    return holidayAPI;
}]);