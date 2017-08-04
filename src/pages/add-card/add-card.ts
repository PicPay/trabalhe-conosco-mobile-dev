import { Component } from '@angular/core';
import { AlertController, IonicPage, NavController, NavParams, ViewController } from 'ionic-angular';

import { Validators, FormBuilder, FormGroup } from '@angular/forms';

import { CardsProvider } from '../../providers/cards/cards';

import moment from 'moment';

@IonicPage()
@Component({
	selector: 'page-add-card',
	templateUrl: 'add-card.html',
})
export class AddCardPage {

	cardForm: FormGroup;
	minCardDate: any;
	maxCardDate: any;

	constructor(
		public alertCtrl: AlertController,

		public formBuilder: FormBuilder,
		public userCards: CardsProvider,

		public navCtrl: NavController,
		public navParams: NavParams,

		public viewCtrl: ViewController
	) {
		this.minCardDate = moment().format("YYYY-MM");
		this.maxCardDate = moment().add(15, "years").format("YYYY-MM");

		this.cardForm = this.formBuilder.group({
			card_number: [null, Validators.required],
			expiry_date: [null, Validators.required],
			cvv: [null, Validators.required],
			description: [null, Validators.required]
		});
	}

	saveCard() {
		if(this.cardForm.valid) {
			let values = this.cardForm.value;

			this.userCards
				.saveCard(
					values["card_number"],
					values["expiry_date"],
					values["cvv"],
					values["description"]
				)
				.then(() => {
					this.alertCtrl.create({
						title: "Cartão cadastrado!",
						subTitle: "Agora você pode utiliza-lo para fazer transações.",
						enableBackdropDismiss: true,
						buttons: [{
							text: "Voltar",
							handler: () => {
								this.viewCtrl.dismiss();
							}
						}]
					}).present();
				})
				.catch((error) => {
					if(error.equalNumber) {
						this.alertCtrl.create({
							title: "Ops.",
							subTitle: "Já existe um cartão cadastrado com esse número!",
							buttons: ["Ok"]
						}).present();
					}
				});
		}
	}
}
