webpackJsonp([2],{

/***/ 393:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MakePaymentPageModule", function() { return MakePaymentPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__make_payment__ = __webpack_require__(399);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var MakePaymentPageModule = (function () {
    function MakePaymentPageModule() {
    }
    return MakePaymentPageModule;
}());
MakePaymentPageModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__make_payment__["a" /* MakePaymentPage */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__make_payment__["a" /* MakePaymentPage */]),
        ],
    })
], MakePaymentPageModule);

//# sourceMappingURL=make-payment.module.js.map

/***/ }),

/***/ 399:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MakePaymentPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(16);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_cards_cards__ = __webpack_require__(313);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__providers_payments_payments__ = __webpack_require__(314);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__providers_utils_utils__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_moment__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_moment___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_moment__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var MakePaymentPage = (function () {
    function MakePaymentPage(alertCtrl, formBuilder, loadingCtrl, payments, userCards, utils, viewCtrl, navCtrl, navParams) {
        this.alertCtrl = alertCtrl;
        this.formBuilder = formBuilder;
        this.loadingCtrl = loadingCtrl;
        this.payments = payments;
        this.userCards = userCards;
        this.utils = utils;
        this.viewCtrl = viewCtrl;
        this.navCtrl = navCtrl;
        this.navParams = navParams;
        this.user = [];
        this.user = navParams.get("userTo");
        this.paymentForm = this.formBuilder.group({
            card: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            value: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
        });
    }
    //makePayment(card, date, user, value) 
    MakePaymentPage.prototype.makePayment = function () {
        var _this = this;
        if (this.paymentForm.valid) {
            var loading_1 = this.loadingCtrl.create({
                content: "Realizando transação, aguarde!"
            });
            loading_1.present();
            var values_1 = this.paymentForm.value;
            this.payments.makePayment(values_1["card"], __WEBPACK_IMPORTED_MODULE_6_moment___default()().format(), this.user, values_1["value"]).then(function () {
                loading_1.dismiss();
                _this.alertCtrl.create({
                    title: "Transação realizada!",
                    subTitle: "Sua transação no valor " + values_1["value"] + " para o usuário " + _this.user.username + " foi realizada com sucesso!",
                    enableBackdropDismiss: true,
                    buttons: [{
                            text: "Voltar",
                            handler: function () {
                                _this.viewCtrl.dismiss();
                            }
                        }]
                }).present();
            }).catch(function (error) {
                loading_1.dismiss();
                var error_text;
                if (error.refused)
                    error_text = "Sua transação não foi autorizada, tente novamente.";
                else
                    error_text = "Falha ao se comunicar com o servidor, tente novamente.";
                _this.alertCtrl.create({
                    title: "Transação falhou!",
                    subTitle: error_text,
                    buttons: ["Ok"]
                }).present();
            });
        }
    };
    return MakePaymentPage;
}());
MakePaymentPage = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPage */])(),
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
        selector: 'page-make-payment',template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\make-payment\make-payment.html"*/'<ion-header>\n	<ion-navbar color="secondary">\n		<ion-title>Realizar Transação</ion-title>\n\n		<ion-buttons start>\n			<button ion-button (click)="viewCtrl.dismiss()">\n				<span ion-text color="primary" showWhen="ios">Cancelar</span>\n				<ion-icon name="md-close" showWhen="android,windows"></ion-icon>\n			</button>\n		</ion-buttons>\n	</ion-navbar>\n</ion-header>\n\n<ion-content no-padding>\n	<form [formGroup]="paymentForm" (ngSubmit)="makePayment()">\n		<ion-list>\n			<ion-item>\n				<ion-avatar item-start>\n					<img src="{{ user.img }}">\n				</ion-avatar>\n\n				<h2>Transferindo para: </h2>\n				<p>{{ user.name }}</p>\n				<p>{{ user.username }}</p>\n			</ion-item>\n\n			<ion-item margin-top>\n				<ion-label floating>Escolher Cartão</ion-label>\n				<ion-select interface="popover" formControlName="card">\n					<ion-option *ngFor="let card of userCards.cards" [value]="card">\n						{{ card.description }} \n						(Final: {{ utils.lastCardDigits(card.card_number) }})\n					</ion-option>\n				</ion-select>\n			</ion-item>\n\n			<ion-item>\n				<ion-label stacked>Valor</ion-label>\n				<ion-input type="number" formControlName="value" placeholder="R$ 0,00"></ion-input>\n			</ion-item>\n		</ion-list>\n	</form>\n\n	<div margin>\n		<button ion-button rounded icon-left block color="secondary" (click)="makePayment()">\n			<ion-icon name="checkmark"></ion-icon>\n			Transferir Valor\n		</button>\n	</div>\n</ion-content>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\make-payment\make-payment.html"*/,
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["a" /* AlertController */],
        __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormBuilder */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["g" /* LoadingController */],
        __WEBPACK_IMPORTED_MODULE_4__providers_payments_payments__["a" /* PaymentsProvider */],
        __WEBPACK_IMPORTED_MODULE_3__providers_cards_cards__["a" /* CardsProvider */],
        __WEBPACK_IMPORTED_MODULE_5__providers_utils_utils__["a" /* UtilsProvider */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["l" /* ViewController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* NavController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["j" /* NavParams */]])
], MakePaymentPage);

//# sourceMappingURL=make-payment.js.map

/***/ })

});
//# sourceMappingURL=2.js.map