angular.module('hadoop').component('hadoop',
  {
      templateUrl: 'hadoop/hadoop.template.html',
      controller: ['$http', '$scope', '$timeout',
        function hadoopController($http, $scope, $timeout) {
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

        $scope.action = function($action) {
            $http({
              method: 'GET',
              url: 'http://localhost/api/hadoop/?action=' + $action
            }).then(function successCallback(response) {
                console.log(response.data);
              }, function errorCallback(response) {
                console.log(response);
              });
            };

        $scope.reload = function () {
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
          $timeout(function(){
            $scope.reload();
          }, 10000)
        };
        $scope.reload();
        }
      ]
  }
  );
