webpackJsonp([4],{

/***/ 391:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "CardsPageModule", function() { return CardsPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__cards__ = __webpack_require__(397);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var CardsPageModule = (function () {
    function CardsPageModule() {
    }
    return CardsPageModule;
}());
CardsPageModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__cards__["a" /* CardsPage */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__cards__["a" /* CardsPage */]),
        ],
    })
], CardsPageModule);

//# sourceMappingURL=cards.module.js.map

/***/ }),

/***/ 397:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CardsPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_cards_cards__ = __webpack_require__(313);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_utils_utils__ = __webpack_require__(51);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var CardsPage = (function () {
    function CardsPage(userCards, modalCtrl, navCtrl, navParams, utils) {
        this.userCards = userCards;
        this.modalCtrl = modalCtrl;
        this.navCtrl = navCtrl;
        this.navParams = navParams;
        this.utils = utils;
    }
    CardsPage.prototype.addCart = function () {
        var addModal = this.modalCtrl.create("AddCardPage");
        addModal.present();
    };
    return CardsPage;
}());
CardsPage = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPage */])(),
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
        selector: 'page-cards',template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\cards\cards.html"*/'<ion-header>\n	<ion-navbar color="dark">\n		<ion-title class="hasIcon">\n			<img src="./assets/picpay-icon.png">\n			Meus Cartões\n		</ion-title>\n	</ion-navbar>\n</ion-header>\n\n\n<ion-content no-padding>\n	<div text-center padding *ngIf="!userCards.cards.length">\n		<h1><ion-icon name="card" large></ion-icon></h1>\n\n		<p><b>Whoooops! Sem nenhum cartão adicionado.</b></p>\n\n		<div padding>\n			<button ion-button outline color="secondary" block small icon-left (click)="addCart()">\n				<ion-icon name="add-circle"></ion-icon>\n				Adicionar seu primeiro cartão!\n			</button>\n		</div>\n	</div>\n\n	<ion-list *ngIf="userCards.cards.length">\n		<ion-item color="primary" (click)="addCart()">\n			<ion-icon name="add" item-start></ion-icon>\n			<h2 text-wrap>Adicionar um cartão!</h2>\n		</ion-item>\n\n		<ion-item *ngFor="let card of userCards.cards">\n			<!--<ion-avatar item-start>\n				<img src="">\n			</ion-avatar>-->\n\n			<h2>{{ card.description }}</h2>\n			<p>Ultimos Dígitos: {{ utils.lastCardDigits(card.card_number) }}</p>\n		</ion-item>\n	</ion-list>\n</ion-content>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\cards\cards.html"*/,
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__providers_cards_cards__["a" /* CardsProvider */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["h" /* ModalController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* NavController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["j" /* NavParams */],
        __WEBPACK_IMPORTED_MODULE_3__providers_utils_utils__["a" /* UtilsProvider */]])
], CardsPage);

//# sourceMappingURL=cards.js.map

/***/ })

});
//# sourceMappingURL=4.js.map