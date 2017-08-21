app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/userList');
    $stateProvider
        .state('userList', {
            url: '/userList',
            templateUrl: 'templates/userList.html',
            controller: "mainController"
        })
        .state('userPayment', {
            url: '/userPayment',
            templateUrl: 'templates/userPayment.html',
            controller: "mainController"
        })
        .state('selectCard', {
            url: '/selectCard',
            templateUrl: 'templates/selectCard.html',
            controller: "mainController"
        })
        .state('addNewCard', {
            url: '/addNewCard',
            templateUrl: 'templates/addNewCard.html',
            controller: "mainController"
        })
        .state('resumeScreen', {
            url: '/resumeScreen',
            templateUrl: 'templates/resumeScreen.html',
            controller: "mainController"
        })
        .state('failScreen', {
            url: '/failScreen',
            templateUrl: 'templates/failScreen.html',
            controller: "mainController"
        });
});