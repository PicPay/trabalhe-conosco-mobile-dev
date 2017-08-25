import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Platform } from 'ionic-angular';
import { Storage } from '@ionic/storage';

import { UtilsProvider } from '../utils/utils';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class UsersDataProvider {

	users: any = [];

	constructor(
		public http: Http,
		public platform: Platform,
		public storage: Storage,

		public utils: UtilsProvider
	) {
		//verifico se a plataforma está preparada para comandos "nativos"
		this.platform.ready().then(() => {
			//verifico e carrego os usuários se existirem
			this.loadUsersFromStorage().then(() => { }).catch(() => { });
		});
	}

	loadUsersFromStorage() {
		return new Promise((resolve, reject) => {
			//checo a existencia na "memoria" do webview/js
			if(this.users.length) {
				resolve(this.users);
			} else {
				this.storage.get("users").then((user_data) => {
					//checo a existencia no dispositivo
					if(user_data) {
						//registro na "memoria" do webview/js
						this.users = user_data;

						resolve(user_data);
					} else {
						reject({ noData: true });
					}
				});
			}			
		});		
	}

	getUsersFromAPI() {
		return new Promise((resolve, reject) => {
			this.http
				.get(this.utils.needCORS() + "http://careers.picpay.com/tests/mobdev/users")
				.toPromise()
				.then((data) => {
					let users_json = data.json();

					console.log("Users from API: ", users_json);
					this.users = users_json;

					this.storage.set("users", users_json);

					resolve(users_json);
				})
				.catch((error) => {
					console.log("Error from Users API: ", error);

					reject(error);
				});
		});
	}
}
