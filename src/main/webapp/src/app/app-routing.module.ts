import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {MainPage} from "./pages/main/main.page";
import {HomePage} from "./pages/home/home.page";
import {LoginPage} from "./pages/login/login.page";
import {AuthGuardService} from "./service/auth-guard/auth-guard.service";

const routes: Routes = [
  {
    path: '', component: MainPage, canActivate: [AuthGuardService]
  },
  {
    path: 'home', component: HomePage
  },
  {
    path: 'login', component: LoginPage
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})
  ],
  exports: [RouterModule],
  providers: [AuthGuardService]
})
export class AppRoutingModule {
}
