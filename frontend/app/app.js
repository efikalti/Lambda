// Define the `phonecatApp` module
var app = angular.module('lambdaApp', [
    'ui.bootstrap',
    'ui.router',
    'ngCookies']);

// Define the `PhoneListController` controller on the `phonecatApp` module
app.controller('PhoneListController', function PhoneListController($scope) {
  $scope.phones = [
    {
      name: 'Nexus S',
      snippet: 'Fast just got faster with Nexus S.'
    }, {
      name: 'Motorola XOOM™ with Wi-Fi',
      snippet: 'The Next, Next Generation tablet.'
    }, {
      name: 'MOTOROLA XOOM™',
      snippet: 'The Next, Next Generation tablet.'
    }
  ];
});
