import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {RouterModule} from "@angular/router";
import {ChartComponent} from "./chart.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule,
  ],
  declarations: [
    ChartComponent
  ],
  exports: [
    ChartComponent
  ]

})
export class ChartComponentModule {
}
