import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DevicePage } from './device.page';
import {InlineEditorComponentModule} from "../../components/inline-editor/inline-editor.module";
import {GraphComponentModule} from "../../components/graph/graph.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    InlineEditorComponentModule,
    GraphComponentModule
  ],
  declarations: [DevicePage]
})
export class DevicePageModule {}
