import { Component } from '@angular/core';
import { AlertController, LoadingController, IonicPage, NavController, NavParams, ViewController } from 'ionic-angular';

import { Validators, FormBuilder, FormGroup } from '@angular/forms';

import { CardsProvider } from '../../providers/cards/cards';
import { PaymentsProvider } from '../../providers/payments/payments';

import { UtilsProvider } from '../../providers/utils/utils';

import moment from 'moment';

@IonicPage()
@Component({
	selector: 'page-make-payment',
	templateUrl: 'make-payment.html',
})
export class MakePaymentPage {

	user: any = [];
	paymentForm: FormGroup;

	constructor(
		public alertCtrl: AlertController,
		public formBuilder: FormBuilder,
		public loadingCtrl: LoadingController,
		public payments: PaymentsProvider,		
		public userCards: CardsProvider,
		public utils: UtilsProvider,
		public viewCtrl: ViewController,

		public navCtrl: NavController,
		public navParams: NavParams
	) {		
		this.user = navParams.get("userTo");

		this.paymentForm = this.formBuilder.group({
			card: [null, Validators.required],
			value: [null, Validators.required],
		});
	}

	//makePayment(card, date, user, value) 

	makePayment() {		
		if(this.paymentForm.valid) {
			let loading = this.loadingCtrl.create({
				content: "Realizando transação, aguarde!"
			});
			loading.present();

			let values = this.paymentForm.value;
			
			this.payments.makePayment(
				values["card"],
				moment().format(),
				this.user,
				values["value"]
			).then(() => {
				loading.dismiss();

				this.alertCtrl.create({
					title: "Transação realizada!",
					subTitle: "Sua transação no valor " + values["value"] + " para o usuário " + this.user.username + " foi realizada com sucesso!",
					enableBackdropDismiss: true,
					buttons: [{
						text: "Voltar",
						handler: () => {
							this.viewCtrl.dismiss();
						}
					}]
				}).present();
			}).catch((error) => {
				loading.dismiss();

				let error_text;
				if(error.refused) error_text = "Sua transação não foi autorizada, tente novamente.";
				else error_text = "Falha ao se comunicar com o servidor, tente novamente.";

				this.alertCtrl.create({
					title: "Transação falhou!",
					subTitle: error_text,
					buttons: ["Ok"]
				}).present();
			});
		}
	}
}
