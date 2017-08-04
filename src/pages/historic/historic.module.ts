import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { HistoricPage } from './historic';

@NgModule({
  declarations: [
    HistoricPage,
  ],
  imports: [
    IonicPageModule.forChild(HistoricPage),
  ],
})
export class HistoricPageModule {}
