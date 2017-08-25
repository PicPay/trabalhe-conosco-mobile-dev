app.factory('UserFactory', function($http){
     $scope.getUsers = function () {
     return $http.get('http://careers.picpay.com/tests/mobdev/users')
         .success(function (data) {
             return data;
         }).error(function (status) {
             return status;
         })
     }
});