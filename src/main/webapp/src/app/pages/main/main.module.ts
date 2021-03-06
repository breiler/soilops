import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';

import {IonicModule} from '@ionic/angular';

import {MainPage} from './main.page';
import {RouterModule} from "@angular/router";
import {DeviceComponentModule} from "../../components/device/device.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule,
    DeviceComponentModule
  ],
  declarations: [
    MainPage
  ]
})
export class MainPageModule {
}
