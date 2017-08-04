import { Injectable } from '@angular/core';
import { Platform } from 'ionic-angular';
import { Storage } from '@ionic/storage';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class CardsProvider {

	cards: any = [];

	constructor(
		public platform: Platform,
		public storage: Storage
	) {
		this.loadCardsFromStorage().then(() => { }).catch(() => { });
	}

	loadCardsFromStorage() {
		return new Promise((resolve, reject) => {
			//checo a existencia na "memoria" do webview/js
			if(this.cards.length) {
				resolve(this.cards);
			} else {
				this.storage.get("cards").then((cards_data) => {
					//checo a existencia no dispositivo
					if(cards_data) {
						//registro na "memoria" do webview/js
						this.cards = cards_data;
						console.log("cards carregados: ", this.cards);

						resolve(cards_data);
					} else {
						reject({ noData: true });
					}
				});
			}			
		});		
	}

	saveCard(number, expiry_date, cvv, description) {
		return new Promise((resolve, reject) => {
			let hasCardAlready = false;

			this.cards.map((card) => {
				if(card.card_number === number) {
					hasCardAlready = true;
				}
			});

			if(!hasCardAlready) {
				this.cards.unshift({
					card_number: number,
					expiry_date: expiry_date,
					cvv: cvv,
					description: description
				});

				this.storage.set("cards", this.cards);

				resolve();
			} else {
				reject({ equalNumber: true });
			}
		});
	}

}