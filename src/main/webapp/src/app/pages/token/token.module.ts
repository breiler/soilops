import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {TokenPage} from './token.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule
  ],
  declarations: [TokenPage],
  entryComponents: [TokenPage]
})
export class TokenPageModule {
}
