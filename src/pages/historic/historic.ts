import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';

import { PaymentsProvider } from '../../providers/payments/payments';

import { UtilsProvider } from '../../providers/utils/utils';

import moment from 'moment';

@IonicPage()
@Component({
	selector: 'page-historic',
	templateUrl: 'historic.html',
})
export class HistoricPage {

	constructor(
		public navCtrl: NavController,
		public navParams: NavParams,
		public payments: PaymentsProvider,

		public utils: UtilsProvider
	) {
		this.payments.loadPaymentsFromStorage().then(() => { }).catch(() => { });
	}

	dateFormated(date) {
		return moment(date).format("DD/MM/YY [às] HH:MM");
	}

}
