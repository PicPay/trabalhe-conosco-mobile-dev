import { Injectable } from '@angular/core';

import { Platform } from 'ionic-angular';

@Injectable()
export class UtilsProvider {

	constructor(
		public platform: Platform
	) {}

	needCORS() {
		if(this.platform.is("mobileweb") || this.platform.is("core")) {
			return "https://cors-anywhere.herokuapp.com/";
		} else return "";
	}

	lastCardDigits(card) {
		let cardString = "" + card + "";

		return cardString.substring(cardString.length-4, cardString.length);
	}

	convertToReal(val) {
		if(val === null || val === undefined) {
			val = 0;
		}

		try {
			val = parseFloat(val);
		} catch (e) {
			//não é string
		}
		

		return val.toLocaleString("pt-BR", { minimumFractionDigits: 2, style: "currency", currency: "BRL" });
	}

}
