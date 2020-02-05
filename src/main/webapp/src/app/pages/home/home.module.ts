import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { HomePage } from './home.page';
import {LoginPageModule} from "../login/login.module";
import {TokenPageModule} from "../token/token.module";
import {InlineEditorComponent} from "../../components/inline-editor/inline-editor.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LoginPageModule,
    TokenPageModule,
    RouterModule.forChild([
      {
        path: '',
        component: HomePage
      }
    ])
  ],
  declarations: [HomePage, InlineEditorComponent]
})
export class HomePageModule {}
