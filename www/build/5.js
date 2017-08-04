webpackJsonp([5],{

/***/ 390:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddCardPageModule", function() { return AddCardPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__add_card__ = __webpack_require__(396);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var AddCardPageModule = (function () {
    function AddCardPageModule() {
    }
    return AddCardPageModule;
}());
AddCardPageModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_2__add_card__["a" /* AddCardPage */],
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__add_card__["a" /* AddCardPage */]),
        ],
    })
], AddCardPageModule);

//# sourceMappingURL=add-card.module.js.map

/***/ }),

/***/ 396:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AddCardPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(16);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_cards_cards__ = __webpack_require__(313);
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





var AddCardPage = (function () {
    function AddCardPage(alertCtrl, formBuilder, userCards, navCtrl, navParams, viewCtrl) {
        this.alertCtrl = alertCtrl;
        this.formBuilder = formBuilder;
        this.userCards = userCards;
        this.navCtrl = navCtrl;
        this.navParams = navParams;
        this.viewCtrl = viewCtrl;
        this.minCardDate = __WEBPACK_IMPORTED_MODULE_4_moment___default()().format("YYYY-MM");
        this.maxCardDate = __WEBPACK_IMPORTED_MODULE_4_moment___default()().add(15, "years").format("YYYY-MM");
        this.cardForm = this.formBuilder.group({
            card_number: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            expiry_date: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            cvv: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            description: [null, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required]
        });
    }
    AddCardPage.prototype.saveCard = function () {
        var _this = this;
        if (this.cardForm.valid) {
            var values = this.cardForm.value;
            this.userCards
                .saveCard(values["card_number"], values["expiry_date"], values["cvv"], values["description"])
                .then(function () {
                _this.alertCtrl.create({
                    title: "Cartão cadastrado!",
                    subTitle: "Agora você pode utiliza-lo para fazer transações.",
                    enableBackdropDismiss: true,
                    buttons: [{
                            text: "Voltar",
                            handler: function () {
                                _this.viewCtrl.dismiss();
                            }
                        }]
                }).present();
            })
                .catch(function (error) {
                if (error.equalNumber) {
                    _this.alertCtrl.create({
                        title: "Ops.",
                        subTitle: "Já existe um cartão cadastrado com esse número!",
                        buttons: ["Ok"]
                    }).present();
                }
            });
        }
    };
    return AddCardPage;
}());
AddCardPage = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPage */])(),
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
        selector: 'page-add-card',template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\add-card\add-card.html"*/'<ion-header>\n	<ion-navbar color="dark">\n		<ion-title>Adicionar um Cartão</ion-title>\n\n		<ion-buttons start>\n			<button ion-button (click)="viewCtrl.dismiss()">\n				<span ion-text color="primary" showWhen="ios">Cancelar</span>\n				<ion-icon name="md-close" showWhen="android,windows"></ion-icon>\n			</button>\n		</ion-buttons>\n	</ion-navbar>\n</ion-header>\n\n\n<ion-content no-padding>\n	<form [formGroup]="cardForm" (ngSubmit)="saveCard()">\n		<ion-list>\n			<ion-item>\n				<ion-label floating>Número do Cartão</ion-label>\n				<ion-input type="number" formControlName="card_number"></ion-input>\n			</ion-item>\n\n			<ion-item>\n				<ion-label floating>Vencimento do Cartão</ion-label>\n				<ion-datetime displayFormat="MM/YYYY" [min]="minCardDate" [max]="maxCardDate" cancelText="Cancelar" doneText="Selecionar" formControlName="expiry_date"></ion-datetime>\n			</ion-item>\n\n			<ion-item>\n				<ion-label floating>CVV</ion-label>\n				<ion-input type="number" formControlName="cvv"></ion-input>\n			</ion-item>\n\n			<ion-item>\n				<ion-label floating>Descrição do Cartão</ion-label>\n				<ion-input type="text" formControlName="description"></ion-input>\n			</ion-item>\n		</ion-list>\n	</form>\n\n	<div margin>\n		<button ion-button rounded icon-left block color="secondary" (click)="saveCard()">\n			<ion-icon name="checkmark"></ion-icon>\n			Salvar Cartão\n		</button>\n	</div>\n</ion-content>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\pages\add-card\add-card.html"*/,
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["a" /* AlertController */],
        __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormBuilder */],
        __WEBPACK_IMPORTED_MODULE_3__providers_cards_cards__["a" /* CardsProvider */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* NavController */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["j" /* NavParams */],
        __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["l" /* ViewController */]])
], AddCardPage);

//# sourceMappingURL=add-card.js.map

/***/ })

});
//# sourceMappingURL=5.js.map