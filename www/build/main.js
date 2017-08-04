webpackJsonp([6],{

/***/ 112:
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	return new Promise(function(resolve, reject) { reject(new Error("Cannot find module '" + req + "'.")); });
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = 112;

/***/ }),

/***/ 154:
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"../pages/add-card/add-card.module": [
		390,
		5
	],
	"../pages/cards/cards.module": [
		391,
		4
	],
	"../pages/historic/historic.module": [
		392,
		3
	],
	"../pages/make-payment/make-payment.module": [
		393,
		2
	],
	"../pages/payment/payment.module": [
		394,
		1
	],
	"../pages/users/users.module": [
		395,
		0
	]
};
function webpackAsyncContext(req) {
	var ids = map[req];
	if(!ids)
		return Promise.reject(new Error("Cannot find module '" + req + "'."));
	return __webpack_require__.e(ids[1]).then(function() {
		return __webpack_require__(ids[0]);
	});
};
webpackAsyncContext.keys = function webpackAsyncContextKeys() {
	return Object.keys(map);
};
module.exports = webpackAsyncContext;
webpackAsyncContext.id = 154;

/***/ }),

/***/ 313:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return CardsProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ionic_storage__ = __webpack_require__(46);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__(80);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_toPromise__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var CardsProvider = (function () {
    function CardsProvider(platform, storage) {
        this.platform = platform;
        this.storage = storage;
        this.cards = [];
        this.loadCardsFromStorage().then(function () { }).catch(function () { });
    }
    CardsProvider.prototype.loadCardsFromStorage = function () {
        var _this = this;
        return new Promise(function (resolve, reject) {
            //checo a existencia na "memoria" do webview/js
            if (_this.cards.length) {
                resolve(_this.cards);
            }
            else {
                _this.storage.get("cards").then(function (cards_data) {
                    //checo a existencia no dispositivo
                    if (cards_data) {
                        //registro na "memoria" do webview/js
                        _this.cards = cards_data;
                        console.log("cards carregados: ", _this.cards);
                        resolve(cards_data);
                    }
                    else {
                        reject({ noData: true });
                    }
                });
            }
        });
    };
    CardsProvider.prototype.saveCard = function (number, expiry_date, cvv, description) {
        var _this = this;
        return new Promise(function (resolve, reject) {
            var hasCardAlready = false;
            _this.cards.map(function (card) {
                if (card.card_number === number) {
                    hasCardAlready = true;
                }
            });
            if (!hasCardAlready) {
                _this.cards.unshift({
                    card_number: number,
                    expiry_date: expiry_date,
                    cvv: cvv,
                    description: description
                });
                _this.storage.set("cards", _this.cards);
                resolve();
            }
            else {
                reject({ equalNumber: true });
            }
        });
    };
    return CardsProvider;
}());
CardsProvider = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["k" /* Platform */],
        __WEBPACK_IMPORTED_MODULE_2__ionic_storage__["b" /* Storage */]])
], CardsProvider);

//# sourceMappingURL=cards.js.map

/***/ }),

/***/ 314:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PaymentsProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(82);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_storage__ = __webpack_require__(46);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__utils_utils__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_moment__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_moment___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_moment__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map__ = __webpack_require__(80);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_rxjs_add_operator_toPromise__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_7_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var PaymentsProvider = (function () {
    function PaymentsProvider(http, platform, storage, utils) {
        this.http = http;
        this.platform = platform;
        this.storage = storage;
        this.utils = utils;
        this.payments = [];
        this.loadPaymentsFromStorage().then(function () { }).catch(function () { });
    }
    PaymentsProvider.prototype.loadPaymentsFromStorage = function () {
        var _this = this;
        return new Promise(function (resolve, reject) {
            //checo a existencia na "memoria" do webview/js
            if (_this.payments.length) {
                resolve(_this.payments);
            }
            else {
                _this.storage.get("payments").then(function (payments_data) {
                    //checo a existencia no dispositivo
                    if (payments_data) {
                        //registro na "memoria" do webview/js
                        _this.payments = payments_data;
                        console.log("payments carregados: ", _this.payments);
                        resolve(payments_data);
                    }
                    else {
                        reject({ noData: true });
                    }
                });
            }
        });
    };
    PaymentsProvider.prototype.savePayment = function (card, date, user, value, success, transaction) {
        this.payments.unshift({
            card: card,
            date: date,
            person: user,
            value: value,
            success: success,
            transaction: transaction
        });
        this.storage.set("payments", this.payments);
    };
    PaymentsProvider.prototype.makePayment = function (card, date, user, value) {
        var _this = this;
        return new Promise(function (resolve, reject) {
            var headers = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Headers */]({ "Content-Type": "application/json" });
            var options = new __WEBPACK_IMPORTED_MODULE_1__angular_http__["d" /* RequestOptions */]({ headers: headers });
            _this.http
                .post(_this.utils.needCORS() + "http://careers.picpay.com/tests/mobdev/transaction", {
                card_number: card.card_number.replace(/\s+/g, ""),
                cvv: parseInt(card.cvv),
                expiry_date: __WEBPACK_IMPORTED_MODULE_5_moment___default()(card.expiry_date).format("DD/MM"),
                value: parseFloat(value),
                destination_user_id: user.id
            }, options)
                .toPromise()
                .then(function (data) {
                var transaction = data.json().transaction;
                _this.savePayment(card, date, user, value, transaction.success, transaction);
                if (transaction.success === false) {
                    reject({ refused: true });
                }
                else {
                    resolve();
                }
            })
                .catch(function (error) {
                console.log("Error from Transaction API: ", error);
                reject({ serverFailure: true });
            });
        });
    };
    return PaymentsProvider;
}());
PaymentsProvider = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */],
        __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["k" /* Platform */],
        __WEBPACK_IMPORTED_MODULE_3__ionic_storage__["b" /* Storage */],
        __WEBPACK_IMPORTED_MODULE_4__utils_utils__["a" /* UtilsProvider */]])
], PaymentsProvider);

//# sourceMappingURL=payments.js.map

/***/ }),

/***/ 315:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UsersDataProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(82);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_storage__ = __webpack_require__(46);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__utils_utils__ = __webpack_require__(51);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_rxjs_add_operator_map__ = __webpack_require__(80);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_toPromise__ = __webpack_require__(81);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_toPromise___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_toPromise__);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var UsersDataProvider = (function () {
    function UsersDataProvider(http, platform, storage, utils) {
        var _this = this;
        this.http = http;
        this.platform = platform;
        this.storage = storage;
        this.utils = utils;
        this.users = [];
        //verifico se a plataforma está preparada para comandos "nativos"
        this.platform.ready().then(function () {
            //verifico e carrego os usuários se existirem
            _this.loadUsersFromStorage().then(function () { }).catch(function () { });
        });
    }
    UsersDataProvider.prototype.loadUsersFromStorage = function () {
        var _this = this;
        return new Promise(function (resolve, reject) {
            //checo a existencia na "memoria" do webview/js
            if (_this.users.length) {
                resolve(_this.users);
            }
            else {
                _this.storage.get("users").then(function (user_data) {
                    //checo a existencia no dispositivo
                    if (user_data) {
                        //registro na "memoria" do webview/js
                        _this.users = user_data;
                        resolve(user_data);
                    }
                    else {
                        reject({ noData: true });
                    }
                });
            }
        });
    };
    UsersDataProvider.prototype.getUsersFromAPI = function () {
        var _this = this;
        return new Promise(function (resolve, reject) {
            _this.http
                .get(_this.utils.needCORS() + "http://careers.picpay.com/tests/mobdev/users")
                .toPromise()
                .then(function (data) {
                var users_json = data.json();
                console.log("Users from API: ", users_json);
                _this.users = users_json;
                _this.storage.set("users", users_json);
                resolve(users_json);
            })
                .catch(function (error) {
                console.log("Error from Users API: ", error);
                reject(error);
            });
        });
    };
    return UsersDataProvider;
}());
UsersDataProvider = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */],
        __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["k" /* Platform */],
        __WEBPACK_IMPORTED_MODULE_3__ionic_storage__["b" /* Storage */],
        __WEBPACK_IMPORTED_MODULE_4__utils_utils__["a" /* UtilsProvider */]])
], UsersDataProvider);

//# sourceMappingURL=users-data.js.map

/***/ }),

/***/ 316:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__ = __webpack_require__(317);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__app_module__ = __webpack_require__(335);


Object(__WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_1__app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 335:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__(82);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__(381);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__ionic_native_status_bar__ = __webpack_require__(309);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ionic_native_splash_screen__ = __webpack_require__(312);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__ionic_storage__ = __webpack_require__(46);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__providers_users_data_users_data__ = __webpack_require__(315);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__providers_payments_payments__ = __webpack_require__(314);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__providers_cards_cards__ = __webpack_require__(313);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__providers_utils_utils__ = __webpack_require__(51);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





//Interface para Plugins Nativos







var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["L" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* MyApp */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["c" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["d" /* IonicModule */].forRoot(__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* MyApp */], {}, {
                links: [
                    { loadChildren: '../pages/add-card/add-card.module#AddCardPageModule', name: 'AddCardPage', segment: 'add-card', priority: 'low', defaultHistory: [] },
                    { loadChildren: '../pages/cards/cards.module#CardsPageModule', name: 'CardsPage', segment: 'cards', priority: 'low', defaultHistory: [] },
                    { loadChildren: '../pages/historic/historic.module#HistoricPageModule', name: 'HistoricPage', segment: 'historic', priority: 'low', defaultHistory: [] },
                    { loadChildren: '../pages/make-payment/make-payment.module#MakePaymentPageModule', name: 'MakePaymentPage', segment: 'make-payment', priority: 'low', defaultHistory: [] },
                    { loadChildren: '../pages/payment/payment.module#PaymentPageModule', name: 'PaymentPage', segment: 'payment', priority: 'low', defaultHistory: [] },
                    { loadChildren: '../pages/users/users.module#UsersPageModule', name: 'UsersPage', segment: 'users', priority: 'low', defaultHistory: [] }
                ]
            }),
            __WEBPACK_IMPORTED_MODULE_7__ionic_storage__["a" /* IonicStorageModule */].forRoot()
        ],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_2_ionic_angular__["b" /* IonicApp */]],
        entryComponents: [
            __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* MyApp */]
        ],
        providers: [
            __WEBPACK_IMPORTED_MODULE_5__ionic_native_status_bar__["a" /* StatusBar */],
            __WEBPACK_IMPORTED_MODULE_6__ionic_native_splash_screen__["a" /* SplashScreen */],
            { provide: __WEBPACK_IMPORTED_MODULE_0__angular_core__["v" /* ErrorHandler */], useClass: __WEBPACK_IMPORTED_MODULE_2_ionic_angular__["c" /* IonicErrorHandler */] },
            __WEBPACK_IMPORTED_MODULE_8__providers_users_data_users_data__["a" /* UsersDataProvider */],
            __WEBPACK_IMPORTED_MODULE_9__providers_payments_payments__["a" /* PaymentsProvider */],
            __WEBPACK_IMPORTED_MODULE_10__providers_cards_cards__["a" /* CardsProvider */],
            __WEBPACK_IMPORTED_MODULE_11__providers_utils_utils__["a" /* UtilsProvider */]
        ]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 363:
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"./af": 155,
	"./af.js": 155,
	"./ar": 156,
	"./ar-dz": 157,
	"./ar-dz.js": 157,
	"./ar-kw": 158,
	"./ar-kw.js": 158,
	"./ar-ly": 159,
	"./ar-ly.js": 159,
	"./ar-ma": 160,
	"./ar-ma.js": 160,
	"./ar-sa": 161,
	"./ar-sa.js": 161,
	"./ar-tn": 162,
	"./ar-tn.js": 162,
	"./ar.js": 156,
	"./az": 163,
	"./az.js": 163,
	"./be": 164,
	"./be.js": 164,
	"./bg": 165,
	"./bg.js": 165,
	"./bn": 166,
	"./bn.js": 166,
	"./bo": 167,
	"./bo.js": 167,
	"./br": 168,
	"./br.js": 168,
	"./bs": 169,
	"./bs.js": 169,
	"./ca": 170,
	"./ca.js": 170,
	"./cs": 171,
	"./cs.js": 171,
	"./cv": 172,
	"./cv.js": 172,
	"./cy": 173,
	"./cy.js": 173,
	"./da": 174,
	"./da.js": 174,
	"./de": 175,
	"./de-at": 176,
	"./de-at.js": 176,
	"./de-ch": 177,
	"./de-ch.js": 177,
	"./de.js": 175,
	"./dv": 178,
	"./dv.js": 178,
	"./el": 179,
	"./el.js": 179,
	"./en-au": 180,
	"./en-au.js": 180,
	"./en-ca": 181,
	"./en-ca.js": 181,
	"./en-gb": 182,
	"./en-gb.js": 182,
	"./en-ie": 183,
	"./en-ie.js": 183,
	"./en-nz": 184,
	"./en-nz.js": 184,
	"./eo": 185,
	"./eo.js": 185,
	"./es": 186,
	"./es-do": 187,
	"./es-do.js": 187,
	"./es.js": 186,
	"./et": 188,
	"./et.js": 188,
	"./eu": 189,
	"./eu.js": 189,
	"./fa": 190,
	"./fa.js": 190,
	"./fi": 191,
	"./fi.js": 191,
	"./fo": 192,
	"./fo.js": 192,
	"./fr": 193,
	"./fr-ca": 194,
	"./fr-ca.js": 194,
	"./fr-ch": 195,
	"./fr-ch.js": 195,
	"./fr.js": 193,
	"./fy": 196,
	"./fy.js": 196,
	"./gd": 197,
	"./gd.js": 197,
	"./gl": 198,
	"./gl.js": 198,
	"./gom-latn": 199,
	"./gom-latn.js": 199,
	"./he": 200,
	"./he.js": 200,
	"./hi": 201,
	"./hi.js": 201,
	"./hr": 202,
	"./hr.js": 202,
	"./hu": 203,
	"./hu.js": 203,
	"./hy-am": 204,
	"./hy-am.js": 204,
	"./id": 205,
	"./id.js": 205,
	"./is": 206,
	"./is.js": 206,
	"./it": 207,
	"./it.js": 207,
	"./ja": 208,
	"./ja.js": 208,
	"./jv": 209,
	"./jv.js": 209,
	"./ka": 210,
	"./ka.js": 210,
	"./kk": 211,
	"./kk.js": 211,
	"./km": 212,
	"./km.js": 212,
	"./kn": 213,
	"./kn.js": 213,
	"./ko": 214,
	"./ko.js": 214,
	"./ky": 215,
	"./ky.js": 215,
	"./lb": 216,
	"./lb.js": 216,
	"./lo": 217,
	"./lo.js": 217,
	"./lt": 218,
	"./lt.js": 218,
	"./lv": 219,
	"./lv.js": 219,
	"./me": 220,
	"./me.js": 220,
	"./mi": 221,
	"./mi.js": 221,
	"./mk": 222,
	"./mk.js": 222,
	"./ml": 223,
	"./ml.js": 223,
	"./mr": 224,
	"./mr.js": 224,
	"./ms": 225,
	"./ms-my": 226,
	"./ms-my.js": 226,
	"./ms.js": 225,
	"./my": 227,
	"./my.js": 227,
	"./nb": 228,
	"./nb.js": 228,
	"./ne": 229,
	"./ne.js": 229,
	"./nl": 230,
	"./nl-be": 231,
	"./nl-be.js": 231,
	"./nl.js": 230,
	"./nn": 232,
	"./nn.js": 232,
	"./pa-in": 233,
	"./pa-in.js": 233,
	"./pl": 234,
	"./pl.js": 234,
	"./pt": 235,
	"./pt-br": 236,
	"./pt-br.js": 236,
	"./pt.js": 235,
	"./ro": 237,
	"./ro.js": 237,
	"./ru": 238,
	"./ru.js": 238,
	"./sd": 239,
	"./sd.js": 239,
	"./se": 240,
	"./se.js": 240,
	"./si": 241,
	"./si.js": 241,
	"./sk": 242,
	"./sk.js": 242,
	"./sl": 243,
	"./sl.js": 243,
	"./sq": 244,
	"./sq.js": 244,
	"./sr": 245,
	"./sr-cyrl": 246,
	"./sr-cyrl.js": 246,
	"./sr.js": 245,
	"./ss": 247,
	"./ss.js": 247,
	"./sv": 248,
	"./sv.js": 248,
	"./sw": 249,
	"./sw.js": 249,
	"./ta": 250,
	"./ta.js": 250,
	"./te": 251,
	"./te.js": 251,
	"./tet": 252,
	"./tet.js": 252,
	"./th": 253,
	"./th.js": 253,
	"./tl-ph": 254,
	"./tl-ph.js": 254,
	"./tlh": 255,
	"./tlh.js": 255,
	"./tr": 256,
	"./tr.js": 256,
	"./tzl": 257,
	"./tzl.js": 257,
	"./tzm": 258,
	"./tzm-latn": 259,
	"./tzm-latn.js": 259,
	"./tzm.js": 258,
	"./uk": 260,
	"./uk.js": 260,
	"./ur": 261,
	"./ur.js": 261,
	"./uz": 262,
	"./uz-latn": 263,
	"./uz-latn.js": 263,
	"./uz.js": 262,
	"./vi": 264,
	"./vi.js": 264,
	"./x-pseudo": 265,
	"./x-pseudo.js": 265,
	"./yo": 266,
	"./yo.js": 266,
	"./zh-cn": 267,
	"./zh-cn.js": 267,
	"./zh-hk": 268,
	"./zh-hk.js": 268,
	"./zh-tw": 269,
	"./zh-tw.js": 269
};
function webpackContext(req) {
	return __webpack_require__(webpackContextResolve(req));
};
function webpackContextResolve(req) {
	var id = map[req];
	if(!(id + 1)) // check for number or string
		throw new Error("Cannot find module '" + req + "'.");
	return id;
};
webpackContext.keys = function webpackContextKeys() {
	return Object.keys(map);
};
webpackContext.resolve = webpackContextResolve;
module.exports = webpackContext;
webpackContext.id = 363;

/***/ }),

/***/ 381:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MyApp; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__ = __webpack_require__(309);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__ = __webpack_require__(312);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var MyApp = (function () {
    function MyApp(platform, statusBar, splashScreen) {
        this.rootPage = "PaymentPage";
        platform.ready().then(function () {
            // Okay, so the platform is ready and our plugins are available.
            // Here you can do any higher level native things you might need.
            statusBar.styleDefault();
            splashScreen.hide();
        });
    }
    return MyApp;
}());
MyApp = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({template:/*ion-inline-start:"D:\WWW\ionic-picpay\PicPayMobileTest\src\app\app.html"*/'<ion-nav [root]="rootPage"></ion-nav>\n'/*ion-inline-end:"D:\WWW\ionic-picpay\PicPayMobileTest\src\app\app.html"*/
    }),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["k" /* Platform */], __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__["a" /* StatusBar */], __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__["a" /* SplashScreen */]])
], MyApp);

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ 51:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UtilsProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var UtilsProvider = (function () {
    function UtilsProvider(platform) {
        this.platform = platform;
    }
    UtilsProvider.prototype.needCORS = function () {
        if (this.platform.is("mobileweb") || this.platform.is("core")) {
            return "https://cors-anywhere.herokuapp.com/";
        }
        else
            return "";
    };
    UtilsProvider.prototype.lastCardDigits = function (card) {
        var cardString = "" + card + "";
        return cardString.substring(cardString.length - 4, cardString.length);
    };
    UtilsProvider.prototype.convertToReal = function (val) {
        if (val === null || val === undefined) {
            val = 0;
        }
        try {
            val = parseFloat(val);
        }
        catch (e) {
            //não é string
        }
        return val.toLocaleString("pt-BR", { minimumFractionDigits: 2, style: "currency", currency: "BRL" });
    };
    return UtilsProvider;
}());
UtilsProvider = __decorate([
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["B" /* Injectable */])(),
    __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["k" /* Platform */]])
], UtilsProvider);

//# sourceMappingURL=utils.js.map

/***/ })

},[316]);
//# sourceMappingURL=main.js.map