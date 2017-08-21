app.controller('mainController', function($http, $scope, $rootScope, $state, $ionicModal){
    window.scope = $scope;
    window.root = $rootScope;
    
    $scope.moneyQuant;
    $scope.card = {}; 
    $rootScope.myCards = [];

    $scope.data = $http.get("http://careers.picpay.com/tests/mobdev/users").success(function(json){
        $scope.response = json;
    });

    $scope.getSelectedUser = function(row) {
		selectedUser = row;
		$rootScope.selectedUser = row;
	};

    $scope.payWithNewCard = function(card) { 
        var card = card;
        $rootScope.myCardArray = [];

        $rootScope.paymentObj = {"card_number": card.cardNumber, "cvv": card.cvv, "value": card.value, "expiry_date": card.expiryDate, "destination_user_id": card.userIdManual};
        $rootScope.newCard = {"card_number": card.cardNumber, "cvv": card.cvv, "expiry_date": card.expiryDate};

        $rootScope.myCardArray.push($rootScope.newCard);
        $rootScope.myCards = $rootScope.myCardArray;

        var dataObj = $scope.paymentObj; 

        $scope.postTransaction(dataObj);        
    }

    $scope.payWithAddedCard = function(card){
        var card = card;
        $rootScope.cardTransaction =  {"card_number": $rootScope.newCard.card_number, "cvv": $rootScope.newCard.cvv, "value": $rootScope.user.value, "expiry_date": $rootScope.newCard.expiryDate, "destination_user_id": $scope.selectedUser.id};
    }

    $scope.postTransaction = function(dataObj){
        var res = $http.post('http://careers.picpay.com/tests/mobdev/transaction', dataObj);
        if(dataObj.card_number == '1111111111111111'){
            res.success(function(data, status, headers, config) {
                $scope.message = data;
                console.log(status, $scope.message);
                $state.go('resumeScreen');
            });
            res.error(function(data, status, headers, config){
                alert( "Erro de transação: " + JSON.stringify({data: data}));
                $scope.message = data;
                console.log(status, $scope.message);
                $state.go('failScreen');
            });	  
        }else{
            $state.go('failScreen');
        }
    }


});