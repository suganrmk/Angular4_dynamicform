mainApp.factory('profileAPIservice', ['$http', '$q',function($http, $q) {
    var profileAPI = {};
    profileAPI.getProfileDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployee']+'?tt='+new Date().getTime()
        });
    };
    profileAPI.getTDCAuth = function(url, userId, token) {
        return $http({
          method: 'get',
          url: url+'api/login?userId='+userId+'&jId='+token
        });
    };
    profileAPI.updPwd = function(passphrase, iv, salt, ciphertext, newPwd) {
        return $http({
          method: 'put',
          headers: {"passphrase":passphrase, "iv":iv, "salt":salt, "ciphertext":ciphertext, "newPwd":newPwd},
          url: perfUrl['updatePwd']
        });
    };
    return profileAPI;
}]);