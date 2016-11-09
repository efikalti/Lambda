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
              $scope.running_applications = arrays.running_applications;
              $scope.number_of_running = arrays.number_of_running_applications;
            }, function errorCallback(response) {
              console.log(response);
            });

        $scope.action = function($action) {
          if ($action === "upload"){
            if ($scope.files == null || $scope.files.length === 0){
                $scope.error = {};
                $scope.error.upload = {};
                $scope.error.upload.status = true;
                $scope.error.upload.message = "No file selected";
            }
            else{
            if($scope.stopped.length === 0){
              var uploadUrl = 'http://localhost/api/hadoop/?action=';
              var fd = new FormData();
              for (var i = 0; i < $scope.files.length; i++) {
                file = $scope.files.item(i);
                filename = 'file' + i;
                fd.append(filename, file);
              }
              $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
              })
              .success(function(){
              })
              .error(function(){
              });
            }
            else{
              $scope.error = {};
              $scope.error.upload = {};
              $scope.error.upload.status = true;
              $scope.error.upload.message = "Some or all of the Hadoop services are not running";
            }
          }
          }
          else
          {
            $http({
              method: 'GET',
              url: 'http://localhost/api/hadoop/?action=' + $action
            }).then(function successCallback(response) {
                console.log(response);
              }, function errorCallback(response) {
                console.log(response);
              });
          }};

        $scope.fileUpload = function (files) {
          $scope.error = {};
          if ($scope.files == null){
            $scope.files = [];
          }
          for (var i = 0; i < files.length; i++) {
            file = files.item(i);
            if($scope.files.indexOf(file) === -1) {
              $scope.files.push(file);
            }
          }
        };

        $scope.clearFiles = function () {
          $scope.error = {};
          if ($scope.files == null || $scope.files.length === 0){
              $scope.error = {};
              $scope.error.upload = {};
              $scope.error.upload.status = true;
              $scope.error.upload.message = "No files added";
          }
          else{
            $scope.files = [];
          }
        };

        $scope.remove = function(file) {
          $scope.error = {};
          for(var i = $scope.files.length; i--;) {
             if($scope.files[i] === file) {
                 $scope.files.splice(i, 1);
             }
         }
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
          }, 5000)
        };
        $scope.reload();
        }
      ]
  }
  );
