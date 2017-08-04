webpackJsonp([0],{

/***/ 395:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersPageModule", function() { return UsersPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__users__ = __webpack_require__(401);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var UsersPageModule = (function () {
    function UsersPageModule() {
    }
    return UsersPageModule;
}());
UsersPageModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__users__["a" /* UsersPage */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__users__["a" /* UsersPage */]),
        ],
    })
], UsersPageModule);

//# sourceMappingURL=users.module.js.map

/***/ }),

/***/ 401:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UsersPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_cards_cards__ = __webpack_require__(313);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_users_data_users_data__ = __webpack_require__(315);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__providers_payments_payments__ = __webpack_require__(314);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var UsersPage = (function () {
    function UsersPage(alertCtrl, usersData, payments, userCards, modalCtrl, navCtrl, navParams) {
        this.alertCtrl = alertCtrl;
        this.usersData = usersData;
        this.payments = payments;
        this.userCards = userCards;
        this.modalCtrl = modalCtrl;
        this.navCtrl = navCtrl;
        this.navParams = navParams;
        this.usersData.getUsersFromAPI().then(function () { }).catch(function () { });
    }
    UsersPage.prototype.syncUsers = function (refresher) {
        this.usersData.getUsersFromAPI().then(function () { return refresher.complete(); }).catch();
    };
    UsersPage.prototype.makePayment = function (user) {
        var _this = this;
        if (this.userCards.cards.length) {
            var paymentModal = this.modalCtrl.create("MakePaymentPage", { userTo: user });
            paymentModal.present();
        }
        else {
            this.alertCtrl.create({
                title: "Ooops!",
                subTitle: "Você ainda não adicionou nenhum cartão.",
                buttons: [{
                        text: "Ok",
                        handler: function () {
                            _this.navCtrl.push("AddCardPage");
                        }
                    }]
            }).present();
        }
    };
    return UsersPage;
}());
UsersPage = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPage */])(),
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
        selector: 'page-users',template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\users\users.html"*/'<ion-header>\n	<ion-navbar color="dark">\n		<ion-title class="hasIcon">\n			<img src="./assets/picpay-icon.png">\n			Fazer Pagamentos\n		</ion-title>\n	</ion-navbar>\n</ion-header>\n\n\n<ion-content no-padding>\n	<ion-refresher (ionRefresh)="syncUsers($event)">\n		<ion-refresher-content\n			pullingText="Puxe para atualizar os usuários"\n			refreshingText="Atualizando..."></ion-refresher-content>\n	</ion-refresher>\n\n	<div text-center padding *ngIf="!usersData.users.length">\n		<ion-icon name="md-contacts" large></ion-icon>\n\n		<p><b>Whoooops! Os contatos ainda não foram carregados.</b></p>\n\n		<p margin-top margin-bottom>\n			Para carregar os contatos basta puxar está página para baixo.\n		</p>\n	</div>\n\n	<ion-list *ngIf="usersData.users.length">\n		<ion-item color="primary">\n			<ion-icon name="hand" item-start></ion-icon>\n			<h2 text-wrap>Toque no usuário para fazer um pagamento!</h2>\n		</ion-item>\n\n		<ion-item *ngFor="let user of usersData.users" (click)="makePayment(user)">\n			<ion-avatar item-start>\n				<img src="{{ user.img }}">\n			</ion-avatar>\n\n			<h2>{{ user.name }}</h2>\n			<p>Usuário: {{ user.username }}</p>\n		</ion-item>\n	</ion-list>\n</ion-content>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\users\users.html"*/,
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["a" /* AlertController */],
        __WEBPACK_IMPORTED_MODULE_3__providers_users_data_users_data__["a" /* UsersDataProvider */],
        __WEBPACK_IMPORTED_MODULE_4__providers_payments_payments__["a" /* PaymentsProvider */],
        __WEBPACK_IMPORTED_MODULE_2__providers_cards_cards__["a" /* CardsProvider */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["h" /* ModalController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* NavController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["j" /* NavParams */]])
], UsersPage);

//# sourceMappingURL=users.js.map

/***/ })

});
//# sourceMappingURL=0.js.map