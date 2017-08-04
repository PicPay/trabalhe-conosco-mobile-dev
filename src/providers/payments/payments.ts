import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import { Platform } from 'ionic-angular';
import { Storage } from '@ionic/storage';

import { UtilsProvider } from '../utils/utils';

import moment from 'moment';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class PaymentsProvider {

	payments: any = [];

	constructor(
		public http: Http,
		public platform: Platform,
		public storage: Storage,

		public utils: UtilsProvider
	) {
		this.loadPaymentsFromStorage().then(() => { }).catch(() => { });
	}


	loadPaymentsFromStorage() {
		return new Promise((resolve, reject) => {
			//checo a existencia na "memoria" do webview/js
			if(this.payments.length) {
				resolve(this.payments);
			} else {
				this.storage.get("payments").then((payments_data) => {
					//checo a existencia no dispositivo
					if(payments_data) {
						//registro na "memoria" do webview/js
						this.payments = payments_data;
						console.log("payments carregados: ", this.payments);

						resolve(payments_data);
					} else {
						reject({ noData: true });
					}
				});
			}			
		});		
	}

	savePayment(card, date, user, value, success, transaction) {
		this.payments.unshift({
			card: card,
			date: date,
			person: user,
			value: value,
			success: success,
			transaction: transaction
		});

		this.storage.set("payments", this.payments);
	}

	makePayment(card, date, user, value) {
		return new Promise((resolve, reject) => {
			let headers = new Headers({ "Content-Type": "application/json" });
			let options = new RequestOptions({ headers: headers });

			this.http
				.post(this.utils.needCORS() + "http://careers.picpay.com/tests/mobdev/transaction", {
					card_number: card.card_number.replace(/\s+/g, ""),
					cvv: parseInt(card.cvv),
					expiry_date: moment(card.expiry_date).format("DD/MM"),

					value: parseFloat(value),
					destination_user_id: user.id
				}, options)
				.toPromise()
				.then((data) => {
					let transaction = data.json().transaction;

					this.savePayment(card, date, user, value, transaction.success, transaction);

					if(transaction.success === false) {
						reject({ refused: true });
					} else {
						resolve();
					}
				})
				.catch((error) => {
					console.log("Error from Transaction API: ", error);

					reject({ serverFailure: true });
				});
		});
	}
}
