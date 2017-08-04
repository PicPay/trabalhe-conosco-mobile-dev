import { Component } from '@angular/core';
import { AlertController, IonicPage, ModalController, NavController, NavParams } from 'ionic-angular';

import { CardsProvider } from '../../providers/cards/cards';
import { UsersDataProvider } from '../../providers/users-data/users-data';
import { PaymentsProvider } from '../../providers/payments/payments';

@IonicPage()
@Component({
	selector: 'page-users',
	templateUrl: 'users.html',
})
export class UsersPage {

	constructor(
		public alertCtrl: AlertController,

		public usersData: UsersDataProvider,
		public payments: PaymentsProvider,
		public userCards: CardsProvider,


		public modalCtrl: ModalController,
		public navCtrl: NavController,
		public navParams: NavParams
	) {
		this.usersData.getUsersFromAPI().then(() => { }).catch(() => { });
	}

	syncUsers(refresher) {
		this.usersData.getUsersFromAPI().then(() => refresher.complete()).catch();
	}

	makePayment(user) {
		if(this.userCards.cards.length) {
			let paymentModal = this.modalCtrl.create(
				"MakePaymentPage",
				{ userTo: user }
			);
			paymentModal.present();
		} else {
			this.alertCtrl.create({
				title: "Ooops!",
				subTitle: "Você ainda não adicionou nenhum cartão.",
				buttons: [{
					text: "Ok",
					handler: () => {
						this.navCtrl.push("AddCardPage");
					}
				}]
			}).present();
		}		
	}
}
