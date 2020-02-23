import {Directive, ElementRef, NgModule, Renderer2, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {RouterModule} from "@angular/router";
import { GraphComponent} from "./graph.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule
  ],
  declarations: [
    GraphComponent
  ],
  exports: [
    GraphComponent
  ]

})
export class GraphComponentModule {

}
