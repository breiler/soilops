import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';
import {BindPageRoutingModule} from './bind-routing.module';
import {BindPage} from './bind.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    BindPageRoutingModule
  ],
  declarations: [BindPage],
  entryComponents: [BindPage]
})
export class BindPageModule {
}
