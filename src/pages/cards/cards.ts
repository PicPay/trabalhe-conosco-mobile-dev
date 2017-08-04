import { Component } from '@angular/core';
import { IonicPage, ModalController, NavController, NavParams } from 'ionic-angular';

import { CardsProvider } from '../../providers/cards/cards';

import { UtilsProvider } from '../../providers/utils/utils';

@IonicPage()
@Component({
	selector: 'page-cards',
	templateUrl: 'cards.html',
})
export class CardsPage {

	constructor(
		public userCards: CardsProvider,

		public modalCtrl: ModalController,
		public navCtrl: NavController,
		public navParams: NavParams,

		public utils: UtilsProvider
	) {

	}

	addCart() {
		let addModal = this.modalCtrl.create(
			"AddCardPage"
		);
		addModal.present();
	}
}
