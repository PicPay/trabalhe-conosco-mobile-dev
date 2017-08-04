webpackJsonp([3],{

/***/ 392:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HistoricPageModule", function() { return HistoricPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__historic__ = __webpack_require__(398);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var HistoricPageModule = (function () {
    function HistoricPageModule() {
    }
    return HistoricPageModule;
}());
HistoricPageModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__historic__["a" /* HistoricPage */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__historic__["a" /* HistoricPage */]),
        ],
    })
], HistoricPageModule);

//# sourceMappingURL=historic.module.js.map

/***/ }),

/***/ 398:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HistoricPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_payments_payments__ = __webpack_require__(314);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_utils_utils__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_moment__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_moment___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_moment__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var HistoricPage = (function () {
    function HistoricPage(navCtrl, navParams, payments, utils) {
        this.navCtrl = navCtrl;
        this.navParams = navParams;
        this.payments = payments;
        this.utils = utils;
        this.payments.loadPaymentsFromStorage().then(function () { }).catch(function () { });
    }
    HistoricPage.prototype.dateFormated = function (date) {
        return __WEBPACK_IMPORTED_MODULE_4_moment___default()(date).format("DD/MM/YY [às] HH:MM");
    };
    return HistoricPage;
}());
HistoricPage = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPage */])(),
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
        selector: 'page-historic',template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\historic\historic.html"*/'<ion-header>\n	<ion-navbar color="dark">\n		<ion-title class="hasIcon">\n			<img src="./assets/picpay-icon.png">\n			Histórico de Transações\n		</ion-title>\n	</ion-navbar>\n</ion-header>\n\n\n<ion-content no-padding>\n	<div text-center padding *ngIf="!payments.payments.length">\n		<ion-icon name="logo-bitcoin" large></ion-icon>\n\n		<p><b>Whoooops! Nenhuma transação foi feita ainda.</b></p>\n	</div>\n\n	<ion-list *ngIf="payments.payments.length">\n		<ion-item *ngFor="let payment of payments.payments">\n			<ion-avatar item-start>\n				<img src="{{ payment.person.img }}">\n\n				<ion-icon name="md-checkmark" class="success" *ngIf="payment.success" item-start></ion-icon>\n				<ion-icon name="md-close" class="fail" *ngIf="!payment.success" item-start></ion-icon>\n			</ion-avatar>\n\n			<h2 *ngIf="payment.success">Transação realizada!</h2>\n			<h2 *ngIf="!payment.success">Transação não realizada!</h2>\n\n			<p text-wrap>\n				Para: {{ payment.person.name }}<br />\n				Valor: {{ utils.convertToReal(payment.value) }}<br />\n				Data: {{ dateFormated(payment.date) }}<br />\n				Cartão: {{ payment.card.description }} <i>({{ utils.lastCardDigits(payment.card.card_number) }})</i>\n			</p>\n		</ion-item>\n	</ion-list>\n</ion-content>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\historic\historic.html"*/,
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* NavController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["j" /* NavParams */],
        __WEBPACK_IMPORTED_MODULE_2__providers_payments_payments__["a" /* PaymentsProvider */],
        __WEBPACK_IMPORTED_MODULE_3__providers_utils_utils__["a" /* UtilsProvider */]])
], HistoricPage);

//# sourceMappingURL=historic.js.map

/***/ })

});
//# sourceMappingURL=3.js.map