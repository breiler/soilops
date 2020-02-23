import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {RouterModule} from "@angular/router";
import {DeviceComponent} from "./device.component";
import {InlineEditorComponentModule} from "../inline-editor/inline-editor.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule,
    InlineEditorComponentModule
  ],
  declarations: [
    DeviceComponent
  ],
  exports: [
    DeviceComponent
  ]

})
export class DeviceComponentModule {
}
