app.config(function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/userList');
    $stateProvider
        .state('userList', {
            url: '/userList',
            templateUrl: 'templates/userList.html'
        })
        .state('userPayment', {
            url: '/userPayment',
            templateUrl: 'templates/userPayment.html'
        })
        .state('selectCard', {
            url: '/selectCard',
            templateUrl: 'templates/selectCard.html'
        })
        .state('addNewCard', {
            url: '/addNewCard',
            templateUrl: 'templates/addNewCard.html'
        })
        .state('resumeScreen', {
            url: '/resumeScreen',
            templateUrl: 'templates/resumeScreen.html'
        });
});