import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { MakePaymentPage } from './make-payment';

@NgModule({
  declarations: [
    MakePaymentPage,
  ],
  imports: [
    IonicPageModule.forChild(MakePaymentPage),
  ],
})
export class MakePaymentPageModule {}
