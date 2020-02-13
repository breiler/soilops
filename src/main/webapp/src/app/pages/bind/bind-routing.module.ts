import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { BindPage } from './bind.page';

const routes: Routes = [
  {
    path: '',
    component: BindPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BindPageRoutingModule {}
