import { Component } from '@angular/core';
import { IonicPage, NavController } from 'ionic-angular';

@IonicPage()
@Component({
	selector: 'page-payment',
	templateUrl: 'payment.html'
})
export class PaymentPage {

	usersRoot = 'UsersPage'
	cardsRoot = 'CardsPage'
	historicRoot = 'HistoricPage'

	constructor(public navCtrl: NavController) {}

}
