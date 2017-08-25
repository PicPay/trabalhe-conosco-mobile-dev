import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { AddCardPage } from './add-card';

@NgModule({
  declarations: [
    AddCardPage,
  ],
  imports: [
    IonicPageModule.forChild(AddCardPage),
  ],
})
export class AddCardPageModule {}
