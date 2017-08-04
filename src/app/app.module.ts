import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { HttpModule } from '@angular/http';
import { MyApp } from './app.component';

//Interface para Plugins Nativos
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { IonicStorageModule } from '@ionic/storage';

import { UsersDataProvider } from '../providers/users-data/users-data';
import { PaymentsProvider } from '../providers/payments/payments';
import { CardsProvider } from '../providers/cards/cards';
import { UtilsProvider } from '../providers/utils/utils';

@NgModule({
	declarations: [
		MyApp
	],
	imports: [
		BrowserModule,
		HttpModule,
		IonicModule.forRoot(MyApp),
		IonicStorageModule.forRoot()
	],
	bootstrap: [IonicApp],
	entryComponents: [
		MyApp
	],
	providers: [
		StatusBar,
		SplashScreen,
		{provide: ErrorHandler, useClass: IonicErrorHandler},

		UsersDataProvider,
		PaymentsProvider,
		CardsProvider,
    UtilsProvider
	]
})
export class AppModule {}
