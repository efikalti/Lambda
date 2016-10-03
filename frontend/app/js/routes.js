'use strict';

angular.module('lambdaApp').config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/dashboard.html'
            })
            .state('hadoop', {
                url: '/hadoop',
                templateUrl: 'templates/hadoop.html'
            });
    }
]);
