app.controller('mainController', function($http, $scope, $rootScope, $state, $ionicModal){
    window.scope = $scope;
    window.root = $rootScope;
    
    $scope.moneyQuant;
    $scope.card = {}; 
    $scope.paymentObj = {"card_number": 1111, "cvv": 152, "expiry_date": 158};
    $scope.myCards = [];
    $scope.data = $http.get("http://careers.picpay.com/tests/mobdev/users").success(function(json){
        $scope.response = json;
    });

    $scope.getSelectedUser = function(row) {
		selectedUser = row;
		$rootScope.selectedUser = row;
	};

    $scope.payWithNewCard = function(card) { 
        var card = card;
        $rootScope.myCards.push(card);
        $scope.paymentObj = {"card_number": card.cardNumber, "cvv": card.cvv, "value": user.moneyQuant, "expiry_date": card.expiryDate, "destination_user_id": card.userIdManual};
        $scope.newCard = {"card_number": card.cardNumber, "cvv": card.cvv, "expiry_date": card.expiryDate};
        var dataObj = $scope.paymentObj;

        var res = $http.post('http://careers.picpay.com/tests/mobdev/transaction ', dataObj);
        if(dataObj.cardNumber === '1111111111111111'){
            res.success(function(data, status, headers, config) {
                $scope.message = data;
                console.log("Transação efetivada! " + $scope.message);
            });
        }
        else {
            res.error(function(data, status, headers, config) {
                alert( "Erro de transação: " + JSON.stringify({data: data}));
            });	
        }
    }
});