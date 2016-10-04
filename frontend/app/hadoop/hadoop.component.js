angular.
  module('hadoop').
  component('hadoop',
  {
      templateUrl: 'hadoop/hadoop.template.html',
      controller: ['$http', '$scope',
        function hadoopController($http, $scope) {
          $http({
            method: 'GET',
            url: 'http://localhost/api/hadoop/?action=check'
          }).then(function successCallback(response) {
              arrays = response.data;
              $scope.running = arrays.running_services;
              $scope.stopped = arrays.stopped_services;
            }, function errorCallback(response) {
              console.log(response);
            });
        }
      ]
  }
  );
